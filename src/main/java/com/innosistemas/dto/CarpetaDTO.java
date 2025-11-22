package com.innosistemas.dto;

public class CarpetaDTO {
    private Integer id;
    private String nombre;
    private Integer proyectoId;
    private Integer padreId;

    public CarpetaDTO() {}
    public CarpetaDTO(Integer id, String nombre, Integer proyectoId, Integer padreId) {
        this.id = id; this.nombre = nombre; this.proyectoId = proyectoId; this.padreId = padreId;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getProyectoId() { return proyectoId; }
    public void setProyectoId(Integer proyectoId) { this.proyectoId = proyectoId; }
    public Integer getPadreId() { return padreId; }
    public void setPadreId(Integer padreId) { this.padreId = padreId; }
}
