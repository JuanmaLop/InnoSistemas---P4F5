package com.innosistemas.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "auditoria_documento")
public class AuditoriaDocumento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditoria_id")
    private Integer id;

    @Column(name = "documento_id", nullable = false)
    private Integer documentoId;

    @Column(name = "accion", nullable = false, length = 50)
    private String accion; // "DESHABILITADO" o "HABILITADO"

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(name = "fecha_accion", nullable = false)
    private Instant fechaAccion;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    // Constructor vacío
    public AuditoriaDocumento() {
    }

    // Constructor con parámetros
    public AuditoriaDocumento(Integer documentoId, String accion, Integer usuarioId, String observaciones) {
        this.documentoId = documentoId;
        this.accion = accion;
        this.usuarioId = usuarioId;
        this.fechaAccion = Instant.now();
        this.observaciones = observaciones;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(Integer documentoId) {
        this.documentoId = documentoId;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Instant getFechaAccion() {
        return fechaAccion;
    }

    public void setFechaAccion(Instant fechaAccion) {
        this.fechaAccion = fechaAccion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
