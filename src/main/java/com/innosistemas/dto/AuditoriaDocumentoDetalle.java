package com.innosistemas.dto;

import java.time.Instant;

public interface AuditoriaDocumentoDetalle {
    Integer getAuditoriaId();
    Integer getDocumentoId();
    String getDocumentoTitulo();
    String getAccion();
    Integer getUsuarioId();
    String getUsuarioNombre();
    String getUsuarioCorreo();
    Instant getFechaAccion();
    String getObservaciones();
}
