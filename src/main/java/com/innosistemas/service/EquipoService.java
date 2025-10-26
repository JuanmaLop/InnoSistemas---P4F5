package com.innosistemas.service;

import org.springframework.stereotype.Service;
import com.innosistemas.entity.Usuario;
import com.innosistemas.entity.Equipo;
import com.innosistemas.entity.UsuarioEquipo;
import com.innosistemas.entity.UsuarioEquipoId;
import com.innosistemas.repository.EquipoRepository;
import com.innosistemas.repository.UsuarioEquipoRepository;
import com.innosistemas.repository.UsuarioRepository;

@Service
public class EquipoService {

    private final UsuarioEquipoRepository usuarioEquipoRepository;
    private final EquipoRepository equipoRepository;
    private final UsuarioRepository usuarioRepository;

    public EquipoService(EquipoRepository equipoRepository, UsuarioRepository usuarioRepository, UsuarioEquipoRepository usuarioEquipoRepository) {
        this.equipoRepository = equipoRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioEquipoRepository = usuarioEquipoRepository;
    }

    public Equipo createEquipoConUsuarios(String nombre, String descripcion, java.util.List<String> correosUsuarios) {
        // Guardar el equipo
        Equipo equipo = new Equipo();
        equipo.setNombre(nombre);
        equipo.setDescripcion(descripcion);
        Equipo equipoGuardado = equipoRepository.save(equipo);

        // Adjuntar cada usuario al equipo
        for (String idUsuario : correosUsuarios) {
            Usuario usuario = usuarioRepository.findByCorreo(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado con correo: " + idUsuario));
            UsuarioEquipo usuarioEquipo = new UsuarioEquipo();
            UsuarioEquipoId usuarioEquipoId = new UsuarioEquipoId();
            usuarioEquipoId.setEquipoId(equipoGuardado.getId());
            usuarioEquipoId.setUsuarioId(usuario.getId());
            usuarioEquipo.setId(usuarioEquipoId);
            usuarioEquipoRepository.save(usuarioEquipo);
        }
        return equipoGuardado;
    }
    
}
