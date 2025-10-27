package com.innosistemas.entity;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class VersionDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "versiond_id")
    private Integer id;

    @Column(name = "documento_id", nullable = false)
    private Integer documentoId;

    @Column(name = "titulo_documento", nullable = false)
    private String tituloDocumento;

    @Column(name = "fecha_version", nullable = false)
    private Date fechaVersion;
    
    @Column(name = "detalles_cambios", nullable = false)
    private String detallesCambios;

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

    public String getTituloDocumento() {
        return tituloDocumento;
    }

    public void setTituloDocumento(String tituloDocumento) {
        this.tituloDocumento = tituloDocumento;
    }

    public Date getFechaVersion() {
        return fechaVersion;
    }

    public void setFechaVersion(Date fechaVersion) {
        this.fechaVersion = fechaVersion;
    }

    public String getDetallesCambios() {
        return detallesCambios;
    }

    public void setDetallesCambios(String detallesCambios) {
        this.detallesCambios = detallesCambios;
    }
}
