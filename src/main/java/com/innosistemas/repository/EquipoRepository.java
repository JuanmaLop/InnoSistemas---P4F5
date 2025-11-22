package com.innosistemas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innosistemas.entity.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {
}
