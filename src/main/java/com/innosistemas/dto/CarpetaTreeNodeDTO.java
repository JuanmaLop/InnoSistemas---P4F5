package com.innosistemas.dto;

import java.util.ArrayList;
import java.util.List;

public class CarpetaTreeNodeDTO {
    private Integer id;
    private String nombre;
    private List<CarpetaTreeNodeDTO> hijos = new ArrayList<>();
    private List<DocumentoSimpleDTO> documentos = new ArrayList<>();

    public CarpetaTreeNodeDTO() {}
    public CarpetaTreeNodeDTO(Integer id, String nombre) { this.id = id; this.nombre = nombre; }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<CarpetaTreeNodeDTO> getHijos() { return hijos; }
    public void setHijos(List<CarpetaTreeNodeDTO> hijos) { this.hijos = hijos; }
    public List<DocumentoSimpleDTO> getDocumentos() { return documentos; }
    public void setDocumentos(List<DocumentoSimpleDTO> documentos) { this.documentos = documentos; }

    public static class DocumentoSimpleDTO {
        private Integer id;
        private String titulo;
        public DocumentoSimpleDTO() {}
        public DocumentoSimpleDTO(Integer id, String titulo) { this.id = id; this.titulo = titulo; }
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }
    }
}
