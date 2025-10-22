package com.innosistemas.security;

/**
 * Utilidad para operaciones con JWT: generación, validación y extracción de usuario.
 */
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private long jwtExpirationMs;

    private SecretKey key;

    /**
     * Inicializa la clave secreta para firmar y validar los tokens JWT.
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Genera un token JWT para el usuario autenticado.
     * @param authentication Autenticación de Spring Security
     * @return Token JWT generado
     */
    public String generateJwtToken(org.springframework.security.core.Authentication authentication) {
        String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
                .getUsername();
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(jwtExpirationMs);

        return Jwts.builder()
            .subject(username)
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiry))
            .signWith(key)
            .compact();
    }

    /**
     * Extrae el nombre de usuario del token JWT.
     * @param token Token JWT
     * @return Nombre de usuario contenido en el token
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    /**
     * Obtiene la fecha de emisión (issuedAt) del token como Instant.
     * @param token Token JWT
     * @return Instant con issuedAt o null si no se puede parsear
     */
    public Instant getIssuedAtFromJwtToken(String token) {
        try {
            Date issuedAt = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload()
                .getIssuedAt();
            return issuedAt != null ? issuedAt.toInstant() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Valida si el token JWT es correcto y no ha expirado.
     * @param authToken Token JWT
     * @return true si el token es válido, false si es inválido o expirado
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(authToken);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
