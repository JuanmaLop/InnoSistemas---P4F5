package com.innosistemas.controller;

import org.springframework.web.bind.annotation.RestController;

import com.innosistemas.entity.Equipo;
import com.innosistemas.entity.Usuario;
import com.innosistemas.entity.UsuarioEquipo;
import com.innosistemas.repository.UsuarioEquipoRepository;
import com.innosistemas.repository.UsuarioRepository;
import com.innosistemas.service.EquipoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.ArrayList;


@RestController
@RequestMapping("/api/equipo")
public class EquipoController {
    private final EquipoService equipoService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioEquipoRepository usuarioequipoRepository;

    public EquipoController(EquipoService equipoService, UsuarioRepository usuarioRepository, UsuarioEquipoRepository usuarioEquipoRepository) {
        this.equipoService = equipoService;
        this.usuarioRepository = usuarioRepository;
        this.usuarioequipoRepository = usuarioEquipoRepository;
    }

    @PostMapping("/crearEquipo")
    public ResponseEntity<Equipo> createEquipo(@RequestParam String nombre, @RequestParam String descripcion, 
            @RequestBody List<String> correosUsuarios, Authentication authentication) {
        // Antes de crear el equipo obtenemos el correo del usuario actual para incluirlo automaricamente en el equipo
        String correoActual = authentication.getName();

        List<String> totalCorreosUsuarios= new ArrayList<>(correosUsuarios);
        if (!totalCorreosUsuarios.contains(correoActual)) {
            totalCorreosUsuarios.add(correoActual);
        }
        Equipo equipo = equipoService.createEquipoConUsuarios(nombre, descripcion, totalCorreosUsuarios);
        return ResponseEntity.ok(equipo);
    }

    @GetMapping("/obtenerMisEquipos")
    public ResponseEntity<?> getMisEquipos(Authentication authentication) {
        // Lógica para obtener los equipos del usuario autenticado
        String correo = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<UsuarioEquipo> usuarioequipos = usuarioequipoRepository.findById_UsuarioId(usuario.getId());     
        List<Equipo> equipos = equipoService.getEquiposPorUsuarioEquipos(usuarioequipos);
        return ResponseEntity.ok(equipos);
    }
}
