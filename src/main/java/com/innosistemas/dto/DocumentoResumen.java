package com.innosistemas.dto;

import java.sql.Date;

/**
 * Proyección para devolver documentos del usuario enriquecidos con nombres de proyecto y equipo.
 */
public interface DocumentoResumen {
    Integer getDocumentoId();
    String getTitulo();
    Date getFechaModificacion();
    Integer getProyectoId();
    String getProyectoNombre();
    Integer getEquipoId();
    String getEquipoNombre();
}
