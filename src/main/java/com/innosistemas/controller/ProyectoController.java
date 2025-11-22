package com.innosistemas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innosistemas.entity.Proyecto;
import com.innosistemas.service.ProyectoService;
import com.innosistemas.entity.Usuario;
import com.innosistemas.repository.UsuarioRepository;
import com.innosistemas.security.AuthorizationService;

@RestController
@RequestMapping("/api/proyecto")
public class ProyectoController {

    private final ProyectoService proyectoService;
    private final UsuarioRepository usuarioRepository;
    private final AuthorizationService authorizationService;

    public ProyectoController(ProyectoService proyectoService, UsuarioRepository usuarioRepository, AuthorizationService authorizationService) {
        this.proyectoService = proyectoService;
        this.usuarioRepository = usuarioRepository;
        this.authorizationService = authorizationService;
    }

    @PostMapping("/crearProyecto")
    public ResponseEntity<?> createProyecto(@RequestParam String nombre, @RequestParam String descripcion, @RequestParam Integer equipoId) {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre);
        proyecto.setDescripcion(descripcion);
        proyecto.setEquipoId(equipoId);
        return ResponseEntity.ok(proyectoService.saveProyecto(proyecto));
    }

    @GetMapping("/obtenerProyectos")
    public ResponseEntity<?> getProyectosByEquipoId(@RequestParam Integer equipoId, Authentication authentication) {
        String correo = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!authorizationService.tieneAccesoAEquipo(usuario.getId(), equipoId)) {
            return ResponseEntity.status(403).body("No tienes permiso para ver los proyectos de este equipo.");
        } else {
            List<Proyecto> proyectos = proyectoService.getProyectosByEquipoId(equipoId);
            return ResponseEntity.ok(proyectos);
        }

    }
}
