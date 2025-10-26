package com.innosistemas.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class UsuarioEquipo {
    @EmbeddedId
    private UsuarioEquipoId id;

    public UsuarioEquipoId getId() {
        return id;
    }

    public void setId(UsuarioEquipoId id) {
        this.id = id;
    }
}
