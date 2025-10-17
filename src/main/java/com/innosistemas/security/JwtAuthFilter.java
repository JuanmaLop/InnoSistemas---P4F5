package com.innosistemas.security;

/**
 * Filtro de autenticación JWT para validar el token en cada petición HTTP.
 * Extiende OncePerRequestFilter para asegurar que se ejecute una vez por solicitud.
 */
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	/**
	 * Utilidad para operaciones con JWT (generar, validar, extraer usuario).
	 */
	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * Servicio para cargar los detalles del usuario desde la base de datos.
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Método principal del filtro. Extrae el token JWT del encabezado Authorization,
	 * valida el token y establece la autenticación en el contexto de seguridad si es válido.
	 *
	 * @param request  Petición HTTP entrante
	 * @param response Respuesta HTTP saliente
	 * @param filterChain Cadena de filtros
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			try {
				username = jwtUtils.getUserNameFromJwtToken(token);
			} catch (Exception e) {
				// Token inválido / expirado
			}
		}

		// Si el usuario existe y no está autenticado, valida el token y autentica
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtUtils.validateJwtToken(token)) {
				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}