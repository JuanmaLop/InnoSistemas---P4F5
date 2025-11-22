package com.innosistemas.service;

import org.springframework.stereotype.Service;
import com.innosistemas.entity.Proyecto;
import com.innosistemas.repository.ProyectoRepository;
import java.util.List;

@Service
public class ProyectoService {
    private final ProyectoRepository proyectoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    public Proyecto saveProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public List<Proyecto> getProyectosByEquipoId(Integer equipoId) {
        return proyectoRepository.findByEquipoId(equipoId);
    }
}
