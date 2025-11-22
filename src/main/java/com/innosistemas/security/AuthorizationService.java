package com.innosistemas.security;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.innosistemas.entity.Equipo;
import com.innosistemas.entity.Proyecto;
import com.innosistemas.repository.EquipoRepository;
import com.innosistemas.repository.ProyectoRepository;
import com.innosistemas.repository.UsuarioEquipoRepository;

@Service
public class AuthorizationService {
    private final ProyectoRepository proyectoRepository;
    private final UsuarioEquipoRepository usuarioEquipoRepository;
    private final EquipoRepository equipoRepository;

    public AuthorizationService(ProyectoRepository proyectoRepository, UsuarioEquipoRepository usuarioEquipoRepository, EquipoRepository equipoRepository) {
        this.proyectoRepository = proyectoRepository;
        this.usuarioEquipoRepository = usuarioEquipoRepository;
        this.equipoRepository = equipoRepository;
    }
    
    // Método para verificar si un usuario esta involucrado en un proyecto
    public boolean tieneAccesoAProyecto(Integer usuarioId, Integer proyectoId) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(proyectoId);
        if (!proyectoOpt.isPresent()) {
            return false;
        }
        Proyecto proyecto = proyectoOpt.get();
        Integer equipoId = proyecto.getEquipoId();
        return usuarioEquipoRepository.findById_UsuarioId(usuarioId)
            .stream()
            .anyMatch(usuarioEquipo -> 
                usuarioEquipo.getId().getEquipoId().equals(equipoId)
            );
    }

    public boolean tieneAccesoAEquipo(Integer usuarioId, Integer equipoId) {
        Optional<Equipo> equipoOpt = equipoRepository.findById(equipoId);
        if (!equipoOpt.isPresent()) {
            return false;
        }
        return usuarioEquipoRepository.findById_UsuarioId(usuarioId)
            .stream()
            .anyMatch(usuarioEquipo -> 
                usuarioEquipo.getId().getEquipoId().equals(equipoId)
            );
    }
}
