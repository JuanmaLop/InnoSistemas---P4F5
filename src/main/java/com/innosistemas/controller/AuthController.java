package com.innosistemas.controller;
import com.innosistemas.security.JwtUtils;
import com.innosistemas.dto.JwtResponse;
import com.innosistemas.dto.LoginRequest;
import com.innosistemas.entity.Usuario;
import com.innosistemas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getCorreo(),
            loginRequest.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    String correo = authentication.getName();

    // Obtener datos adicionales del usuario
    Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);
    String nombre = usuario != null ? usuario.getNombres() : "";
    String apellidos = usuario != null ? usuario.getApellidos() : "";
    String rol = usuario != null && usuario.getRol() != null ? usuario.getRol().toString() : "";

    return new JwtResponse(jwt, correo, nombre, apellidos, rol);
    }

    /**
     * Cierra la sesión del usuario actual invalidando tokens anteriores.
     * Establece tokenInvalidBefore = now() para el usuario.
     */
    @PostMapping("/logout")
    public void logoutCurrentUser(Authentication authentication) {
        if (authentication == null) return;
        String correo = authentication.getName();
        usuarioRepository.findByCorreo(correo).ifPresent(u -> {
            u.setTokenInvalidBefore(java.time.Instant.now());
            usuarioRepository.save(u);
        });
    }
}