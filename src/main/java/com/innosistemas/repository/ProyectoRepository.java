package com.innosistemas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innosistemas.entity.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    Optional<Proyecto> findByIdAndEquipoId(Integer proyectoId, Integer equipoId);
    List<Proyecto> findByEquipoId(Integer equipoId);
}
