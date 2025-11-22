package com.innosistemas.controller;

import java.util.List;

import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.innosistemas.dto.DocumentoResumen;
import com.innosistemas.entity.Documento;
import com.innosistemas.entity.Usuario;
import com.innosistemas.repository.DocumentoRepository;
import com.innosistemas.repository.UsuarioRepository;
import com.innosistemas.security.AuthorizationService;
import com.innosistemas.service.DocumentoService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api/documento")
public class DocumentoController {

    private final DocumentoService documentoService;
    private final DocumentoRepository documentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthorizationService authorizationService;

    public DocumentoController(DocumentoService documentoService, DocumentoRepository documentoRepository,
            UsuarioRepository usuarioRepository, AuthorizationService authorizationService) {
        this.documentoService = documentoService;
        this.documentoRepository = documentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.authorizationService = authorizationService;
    }

    @PostMapping(path = "/subirDocumento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadDocumento(@RequestParam("file") MultipartFile file, @RequestParam("titulo") String titulo,
            @RequestParam("proyectoId") Integer proyectoId, Authentication authentication) {
        // Primero verificamos que el usuario tenga permiso para subir el documento al proyecto
        String correo = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!authorizationService.tieneAccesoAProyecto(usuario.getId(), proyectoId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para subir documentos a este proyecto.");
        } else {
            // Usuario autorizado, procedemos a subir el documento
            try {
                Documento docSaved = documentoService.uploadAndSaveDocumento(file, titulo, proyectoId, usuario.getId());
                return ResponseEntity.status(HttpStatus.CREATED).body(docSaved);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    }

    @PostMapping(path = "/{documentoId}/actualizarDocumento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateDocumento(@PathVariable Integer documentoId, @RequestParam("file") MultipartFile file,
            @RequestParam("nuevoTitulo") String nuevoTitulo, @RequestParam("detallesCambios") String detallesCambios, Authentication authentication) {
        // Verificamos que el usuario tenga permiso para editar el documento al proyecto
        Documento documentoAct = documentoRepository.findById(documentoId).orElseThrow(() -> new RuntimeException("Documento no encontrado"));
        String correo = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!authorizationService.tieneAccesoAProyecto(usuario.getId(), documentoAct.getProyectoId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar documentos de este proyecto.");
        } else {
            // Usuario autorizado, procedemos a subir el documento
            try {
                Documento docUpdated = documentoService.updateDocumento(documentoId, file, nuevoTitulo, usuario.getId(), detallesCambios);
                return ResponseEntity.ok(docUpdated);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    }

    @GetMapping("/{proyectoId}/documentosPorProyecto")
    public ResponseEntity<?> getDocumentosPorProyecto(@PathVariable Integer proyectoId, Authentication authentication) {
        // Primero verificamos que el usuario tenga permiso para obtener documentos del proyecto
        String correo = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!authorizationService.tieneAccesoAProyecto(usuario.getId(), proyectoId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para acceder los documentos de este proyecto.");
        } else {
            // Usuario autorizado, procedemos a obtener los documentos del proyecto
            try {
                List<Documento> documentos = documentoService.findAllDocumentosByProyectoId(proyectoId);
                return ResponseEntity.ok(documentos);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    }

    @GetMapping("/mis-documentos")
    public ResponseEntity<?> getMisDocumentos(Authentication authentication) {
        String correo = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        try {
            List<DocumentoResumen> documentos = documentoService.findResumenByUsuarioId(usuario.getId());
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{documentoId}/descargarDocumento")
    public ResponseEntity<?> downloadDocumento(@PathVariable Integer documentoId, Authentication authentication)
            throws IOException, java.io.IOException {
        // Primero verificamos que el usuario tenga permiso para descargar el documento
        Documento doc = documentoRepository.findById(documentoId).orElseThrow(() -> new RuntimeException("Documento no encontrado"));
        String correo = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!authorizationService.tieneAccesoAProyecto(usuario.getId(), doc.getProyectoId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para acceder a este documento.");
        } else {
            // Usuario autorizado, procedemos a descargar el archivo desde GridFS
            String gridFsId = doc.getRutaDoc();
            GridFsResource resource = documentoService.downloadByGridFsId(gridFsId);
            String contentType = resource.getContentType();
            String filename = resource.getFilename();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\"");

            return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        }
    }

    @DeleteMapping("/{documentoId}/deshabilitarDocumento")
    public ResponseEntity<?> deshabilitarDocumento(@PathVariable Integer documentoId, Authentication authentication) {
        // Verificamos que el usuario tenga permiso para deshabilitar el documento
        Documento doc = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
        String correo = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!authorizationService.tieneAccesoAProyecto(usuario.getId(), doc.getProyectoId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para eliminar documentos de este proyecto.");
        } else {
            try {
                documentoService.deshabilitarDocumento(documentoId, usuario.getId());
                return ResponseEntity.ok("Documento deshabilitado exitosamente.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al deshabilitar el documento: " + e.getMessage());
            }
        }

    }

    @PutMapping("/{documentoId}/restaurarDocumento")
    public ResponseEntity<?> habilitarDocumento(@PathVariable Integer documentoId, Authentication authentication) {
        // Verificamos que el usuario tenga permiso para habilitar el documento
        Documento doc = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
        String correo = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!authorizationService.tieneAccesoAProyecto(usuario.getId(), doc.getProyectoId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para restaurar documentos de este proyecto.");
        } else {
            try {
                documentoService.habilitarDocumento(documentoId, usuario.getId());
                return ResponseEntity.ok("Documento restaurado exitosamente.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al restaurar el documento: " + e.getMessage());
            }
        }
    }
}
