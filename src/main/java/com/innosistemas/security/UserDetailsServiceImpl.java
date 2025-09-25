package com.innosistemas.security;
import com.innosistemas.repository.UsuarioRepository;
import com.innosistemas.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + correo));

        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()));

        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreo(),
                usuario.getContrasenia(),
                authorities
        );
    }
}
