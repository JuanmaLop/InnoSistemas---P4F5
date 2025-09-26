package com.innosistemas.dto;

public class JwtResponse {
    private String token;
    private String correo;
    private String nombres;
    private String apellidos;
    private String rol;

    public JwtResponse(String token, String correo, String nombres, String apellidos, String rol) {
        this.token = token;
        this.correo = correo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}