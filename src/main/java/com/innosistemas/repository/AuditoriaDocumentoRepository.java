package com.innosistemas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.innosistemas.dto.AuditoriaDocumentoDetalle;
import com.innosistemas.entity.AuditoriaDocumento;

@Repository
public interface AuditoriaDocumentoRepository extends JpaRepository<AuditoriaDocumento, Integer> {
    
    // Obtener todas las auditorías de un documento específico
    List<AuditoriaDocumento> findByDocumentoIdOrderByFechaAccionDesc(Integer documentoId);
    
    // Obtener todas las acciones realizadas por un usuario
    List<AuditoriaDocumento> findByUsuarioIdOrderByFechaAccionDesc(Integer usuarioId);
    
    // Obtener la última acción realizada sobre un documento
    @Query("SELECT a FROM AuditoriaDocumento a WHERE a.documentoId = :documentoId ORDER BY a.fechaAccion DESC LIMIT 1")
    AuditoriaDocumento findUltimaAccionByDocumentoId(@Param("documentoId") Integer documentoId);
    
    // Obtener auditorías por tipo de acción
    List<AuditoriaDocumento> findByAccionOrderByFechaAccionDesc(String accion);
    
    // Obtener historial completo con información del usuario que realizó la acción
    @Query("SELECT a.id as auditoriaId, a.documentoId as documentoId, d.titulo as documentoTitulo, " +
           "a.accion as accion, a.usuarioId as usuarioId, u.nombres as usuarioNombre, " +
           "u.correo as usuarioCorreo, a.fechaAccion as fechaAccion, a.observaciones as observaciones " +
           "FROM AuditoriaDocumento a " +
           "JOIN Usuario u ON a.usuarioId = u.id " +
           "JOIN Documento d ON a.documentoId = d.id " +
           "WHERE a.documentoId = :documentoId " +
           "ORDER BY a.fechaAccion DESC")
    List<AuditoriaDocumentoDetalle> findDetalleByDocumentoId(@Param("documentoId") Integer documentoId);
}
