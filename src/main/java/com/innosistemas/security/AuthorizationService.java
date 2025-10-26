package com.innosistemas.security;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.innosistemas.repository.ProyectoRepository;
import com.innosistemas.repository.UsuarioEquipoRepository;
import com.innosistemas.entity.Proyecto;

@Service
public class AuthorizationService {
    private final ProyectoRepository proyectoRepository;
    private final UsuarioEquipoRepository usuarioEquipoRepository;

    public AuthorizationService(ProyectoRepository proyectoRepository, UsuarioEquipoRepository usuarioEquipoRepository) {
        this.proyectoRepository = proyectoRepository;
        this.usuarioEquipoRepository = usuarioEquipoRepository;
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
}
