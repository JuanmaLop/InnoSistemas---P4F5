package com.innosistemas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innosistemas.dto.AuditoriaDocumentoDetalle;
import com.innosistemas.entity.Documento;
import com.innosistemas.entity.Usuario;
import com.innosistemas.repository.AuditoriaDocumentoRepository;
import com.innosistemas.repository.DocumentoRepository;
import com.innosistemas.repository.UsuarioRepository;
import com.innosistemas.security.AuthorizationService;

@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaDocumentoController {

    private final AuditoriaDocumentoRepository auditoriaDocumentoRepository;
    private final DocumentoRepository documentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthorizationService authorizationService;

    public AuditoriaDocumentoController(AuditoriaDocumentoRepository auditoriaDocumentoRepository,
            DocumentoRepository documentoRepository, UsuarioRepository usuarioRepository,
            AuthorizationService authorizationService) {
        this.auditoriaDocumentoRepository = auditoriaDocumentoRepository;
        this.documentoRepository = documentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.authorizationService = authorizationService;
    }

    // Obtener historial de auditoría de un documento
    @GetMapping("/documento/{documentoId}")
    public ResponseEntity<?> obtenerHistorialDocumento(@PathVariable Integer documentoId,
            Authentication authentication) {
        // Verificar que el documento existe
        Documento documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        // Verificar que el usuario tiene acceso al proyecto
        String correo = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!authorizationService.tieneAccesoAProyecto(usuario.getId(), documento.getProyectoId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para ver el historial de este documento.");
        }

        try {
            List<AuditoriaDocumentoDetalle> historial = auditoriaDocumentoRepository
                    .findDetalleByDocumentoId(documentoId);
            return ResponseEntity.ok(historial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el historial: " + e.getMessage());
        }
    }
}
