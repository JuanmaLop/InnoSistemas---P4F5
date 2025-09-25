package com.innosistemas.security;
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

    @PostConstruct
    public void init() {
    this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

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

    public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().verifyWith(key).build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(authToken);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
