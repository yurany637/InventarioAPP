package com.inventario.InventarioAPP.config;

import com.inventario.InventarioAPP.entidad.Usuario;
import com.inventario.InventarioAPP.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existe el usuario admin
        if (usuarioRepository.findByNombreUsuario("admin") == null) {
            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            admin.setPassword(passwordEncoder.encode("1234")); // Contraseña encriptada
            admin.setRol("ADMIN");
            usuarioRepository.save(admin);
            System.out.println("✅ Usuario administrador creado:");
            System.out.println("   Usuario: admin");
            System.out.println("   Contraseña: 1234");
        }

        // Crear un usuario normal de ejemplo
        if (usuarioRepository.findByNombreUsuario("usuario1") == null) {
            Usuario user = new Usuario();
            user.setNombreUsuario("usuario1");
            user.setPassword(passwordEncoder.encode("pass123")); // Contraseña encriptada
            user.setRol("USER");
            usuarioRepository.save(user);
            System.out.println("✅ Usuario normal creado:");
            System.out.println("   Usuario: usuario1");
            System.out.println("   Contraseña: pass123");
        }
    }
}