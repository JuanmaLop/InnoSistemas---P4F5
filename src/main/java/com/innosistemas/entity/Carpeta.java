package com.innosistemas.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Carpeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carpeta_id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "proyecto_id", nullable = false)
    private Integer proyectoId;

    @Column(name = "padre_id")
    private Integer padreId;

    @Column(name = "usuario_creador_id", nullable = false)
    private Integer usuarioCreadorId;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "Habilitado";

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getProyectoId() { return proyectoId; }
    public void setProyectoId(Integer proyectoId) { this.proyectoId = proyectoId; }

    public Integer getPadreId() { return padreId; }
    public void setPadreId(Integer padreId) { this.padreId = padreId; }

    public Integer getUsuarioCreadorId() { return usuarioCreadorId; }
    public void setUsuarioCreadorId(Integer usuarioCreadorId) { this.usuarioCreadorId = usuarioCreadorId; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
