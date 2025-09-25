package com.innosistemas.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {
    @GetMapping("/usuario/info")
    public ResponseEntity<String> getUserInfo() {
        return ResponseEntity.ok("PRUEBA: Información del usuario autenticado :)");
    }
}
