package com.innosistemas.security;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración de seguridad para la aplicación.
 * Define la política de autenticación, filtros y codificador de contraseñas.
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	/**
	 * Filtro para validar el JWT en cada petición.
	 */
	@Autowired
	private JwtAuthFilter jwtAuthFilter;

	/**
	 * Configura la cadena de filtros de seguridad y las reglas de acceso.
	 *
	 * @param http Configuración HTTP de Spring Security
	 * @return SecurityFilterChain configurada
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				// Rutas públicas
				.requestMatchers("/auth/**").permitAll()
				.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
				// Ejemplo de rutas por rol (ajusta las rutas según tu aplicación):
				// - Rutas administrativas
				.requestMatchers("/admin/**").hasRole("ADMIN")
				// - Rutas para profesores
				.requestMatchers("/profesor/**").hasAnyRole("PROFESOR", "ADMIN")
				// - Rutas para estudiantes
				.requestMatchers("/estudiante/**").hasAnyRole("ESTUDIANTE", "PROFESOR", "ADMIN")
				// El resto requiere autenticación
				.anyRequest().authenticated()
			)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	/**
	 * Bean para obtener el AuthenticationManager de la configuración.
	 *
	 * @param authConfig Configuración de autenticación
	 * @return AuthenticationManager
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	/**
	 * Bean para codificar contraseñas usando BCrypt.
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}