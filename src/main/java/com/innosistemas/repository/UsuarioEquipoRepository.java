package com.innosistemas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.innosistemas.entity.UsuarioEquipo;
import com.innosistemas.entity.UsuarioEquipoId;

@Repository
public interface UsuarioEquipoRepository extends JpaRepository<UsuarioEquipo, UsuarioEquipoId> {
    List<UsuarioEquipo> findById_UsuarioId(Integer usuarioId);
    List<UsuarioEquipo> findById_EquipoId(Integer equipoId);
}
