package com.innosistemas.entity;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UsuarioEquipoId implements Serializable {
    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "equipo_id")
    private Integer equipoId;

    public UsuarioEquipoId() {
    }

    public UsuarioEquipoId(Integer usuarioId, Integer equipoId) {
        this.usuarioId = usuarioId;
        this.equipoId = equipoId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Integer equipoId) {
        this.equipoId = equipoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UsuarioEquipoId that = (UsuarioEquipoId) o;
        return Objects.equals(usuarioId, that.usuarioId) &&
                Objects.equals(equipoId, that.equipoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, equipoId);
    }
}
