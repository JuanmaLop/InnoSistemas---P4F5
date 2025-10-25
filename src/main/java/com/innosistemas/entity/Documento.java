package com.innosistemas.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "documento_id")
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "ruta_doc", nullable = false)
    private String rutaDoc;

    @Column(name = "proyecto_id", nullable = false)
    private Integer proyectoId;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(name = "fecha_modificacion", nullable = false)
    private Date fechaModificacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRutaDoc() {
        return rutaDoc;
    }

    public void setRutaDoc(String rutaDoc) {
        this.rutaDoc = rutaDoc;
    }

    public Integer getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Integer proyectoId) {
        this.proyectoId = proyectoId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
