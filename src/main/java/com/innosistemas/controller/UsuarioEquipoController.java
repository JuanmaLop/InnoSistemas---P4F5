package com.innosistemas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innosistemas.repository.UsuarioEquipoRepository;
import com.innosistemas.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuarioEquipo")
public class UsuarioEquipoController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioEquipoRepository usuarioequipoRepository;

    public UsuarioEquipoController(UsuarioRepository usuarioRepository, UsuarioEquipoRepository usuarioEquipoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioequipoRepository = usuarioEquipoRepository;
    }
}
