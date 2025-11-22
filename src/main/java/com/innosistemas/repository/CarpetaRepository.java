package com.innosistemas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.innosistemas.entity.Carpeta;

public interface CarpetaRepository extends JpaRepository<Carpeta, Integer> {
    List<Carpeta> findByProyectoIdAndPadreId(Integer proyectoId, Integer padreId);
    boolean existsByProyectoIdAndPadreIdAndNombre(Integer proyectoId, Integer padreId, String nombre);
}
