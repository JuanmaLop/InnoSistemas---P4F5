package com.innosistemas.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.innosistemas.dto.DocumentoResumen;
import com.innosistemas.entity.Documento;


@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer>{
    List<Documento> findByProyectoIdAndEstado(Integer proyectoId, String estado);
    
    List<Documento> findByProyectoId(Integer proyectoId);

    List<Documento> findByProyectoIdAndCarpetaId(Integer proyectoId, Integer carpetaId);
    List<Documento> findByProyectoIdAndCarpetaIdIsNull(Integer proyectoId);

    /**
     * Devuelve los documentos subidos por un usuario junto con nombres de proyecto y equipo.
     * Usa JPQL con entidades sin relaciones (join por igualdad de ids).
     * Solo devuelve documentos habilitados.
     */
    @Query("""
        select 
            d.id as documentoId,
            d.titulo as titulo,
            d.fechaModificacion as fechaModificacion,
            p.id as proyectoId,
            p.nombre as proyectoNombre,
            e.id as equipoId,
            e.nombre as equipoNombre
        from Documento d, Proyecto p, Equipo e
        where p.id = d.proyectoId
          and e.id = p.equipoId
          and d.usuarioId = :usuarioId
        order by d.fechaModificacion desc
    """)
    List<DocumentoResumen> findResumenByUsuarioId(@Param("usuarioId") Integer usuarioId);
}
