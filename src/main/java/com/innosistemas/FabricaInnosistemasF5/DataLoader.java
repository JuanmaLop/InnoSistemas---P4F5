package com.innosistemas.FabricaInnosistemasF5;
import com.innosistemas.entity.Usuario;
import com.innosistemas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/* Aquí se inserta un usuario a la base de datos*/
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        /*try (Connection c = dataSource.getConnection()) {
            System.out.println("DataSource conectado: " + c.getMetaData().getURL() +
                    " - Usuario: " + c.getMetaData().getUserName());
        }
        String correo = "test@uni.edu";
        if (usuarioRepository.findByCorreo(correo).isEmpty()) {
            Usuario u = new Usuario();
            u.setNombres("Usuario");
            u.setApellidos("Prueba");
            u.setCorreo(correo);
            u.setContrasenia(passwordEncoder.encode("Password123")); // contraseña de prueba
            u.setRol(1);; // Asignar rol de usuario estándar
            usuarioRepository.save(u);
            System.out.println("Usuario de prueba creado: " + correo + " / Password123");
        }*/
    }
}
