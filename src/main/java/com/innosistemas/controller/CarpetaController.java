package com.innosistemas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innosistemas.dto.CarpetaDTO;
import com.innosistemas.dto.CarpetaTreeNodeDTO;
import com.innosistemas.entity.Usuario;
import com.innosistemas.repository.UsuarioRepository;
import com.innosistemas.security.AuthorizationService;
import com.innosistemas.service.CarpetaService;

@RestController
@RequestMapping("/api/carpetas")
public class CarpetaController {

    private final CarpetaService carpetaService;
    private final UsuarioRepository usuarioRepository;
    private final AuthorizationService authorizationService;

    public CarpetaController(CarpetaService carpetaService, UsuarioRepository usuarioRepository, AuthorizationService authorizationService) {
        this.carpetaService = carpetaService;
        this.usuarioRepository = usuarioRepository;
        this.authorizationService = authorizationService;
    }

    @PostMapping("/proyectos/{proyectoId}/crearCarpetaRaiz")
    public ResponseEntity<?> crearRaiz(@PathVariable Integer proyectoId, @RequestBody CrearCarpetaRequest request, Authentication auth) {
        Usuario usuario = usuarioRepository.findByCorreo(auth.getName()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!authorizationService.tieneAccesoAProyecto(usuario.getId(), proyectoId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sin acceso al proyecto");
        } else {
            CarpetaDTO dto = carpetaService.crearCarpeta(proyectoId, null, request.getNombre(), usuario.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }
    }

    @PostMapping("/proyectos/{proyectoId}/{carpetaId}/crearCarpetaSub")
    public ResponseEntity<?> crearSub(@PathVariable Integer proyectoId, @PathVariable Integer carpetaId, @RequestBody CrearCarpetaRequest request, Authentication auth) {
        Usuario usuario = usuarioRepository.findByCorreo(auth.getName()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!authorizationService.tieneAccesoAProyecto(usuario.getId(), proyectoId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sin acceso al proyecto");
        } else {
            CarpetaDTO dto = carpetaService.crearCarpeta(proyectoId, carpetaId, request.getNombre(), usuario.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }

    }

    @GetMapping("/proyectos/{proyectoId}/obtenerTreeCarpeta")
    public ResponseEntity<?> obtenerTree(@PathVariable Integer proyectoId, 
                                         @RequestParam(name = "incluirDocumentos", defaultValue = "true") boolean incluirDocumentos,
                                         Authentication auth) {
        Usuario usuario = usuarioRepository.findByCorreo(auth.getName()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!authorizationService.tieneAccesoAProyecto(usuario.getId(), proyectoId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sin acceso al proyecto");
        } else {
            CarpetaTreeNodeDTO tree = carpetaService.obtenerArbolProyecto(proyectoId, incluirDocumentos);
            return ResponseEntity.ok(tree);
        }
    }

    public static class CrearCarpetaRequest {

        private String nombre;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }
}
