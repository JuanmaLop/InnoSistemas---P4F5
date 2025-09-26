package com.innosistemas.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.innosistemas.entity.Usuario;
import com.innosistemas.repository.UsuarioRepository;
import java.security.Principal;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/me")
    public ResponseEntity<Usuario> getUserInfo(Principal principal) {
        Usuario u = usuarioRepository.findByCorreo(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        u.setContrasenia(null); // No mostramos la contraseña aunque este hasheada
        return ResponseEntity.ok(u);
    }
}
