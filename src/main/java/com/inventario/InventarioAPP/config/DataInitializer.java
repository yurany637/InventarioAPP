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
        System.out.println("========================================");
        System.out.println("🔄 Iniciando DataInitializer...");
        System.out.println("========================================");

        // Verificar y crear usuario admin
        Usuario adminExistente = usuarioRepository.findByUsername("admin");
        if (adminExistente == null) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            String passwordEncriptada = passwordEncoder.encode("123");
            admin.setPassword(passwordEncriptada);
            usuarioRepository.save(admin);

            System.out.println("✅ Usuario administrador creado:");
            System.out.println("   Usuario: admin");
            System.out.println("   Contraseña: 123");
            System.out.println("   Password Hash: " + passwordEncriptada);
        } else {
            System.out.println("ℹ️  Usuario 'admin' ya existe en la base de datos");
            System.out.println("   Password Hash actual: " + adminExistente.getPassword());

            // OPCIONAL: Actualizar contraseña si es necesario
            // Descomenta las siguientes líneas si quieres resetear la contraseña del admin
            /*
            String nuevaPassword = passwordEncoder.encode("123");
            adminExistente.setPassword(nuevaPassword);
            usuarioRepository.save(adminExistente);
            System.out.println("🔄 Contraseña de admin actualizada");
            */
        }

        // Verificar y crear usuario normal
        Usuario usuarioExistente = usuarioRepository.findByUsername("usuario1");
        if (usuarioExistente == null) {
            Usuario user = new Usuario();
            user.setUsername("usuario1");
            String passwordEncriptada = passwordEncoder.encode("pass123");
            user.setPassword(passwordEncriptada);
            usuarioRepository.save(user);

            System.out.println("✅ Usuario normal creado:");
            System.out.println("   Usuario: usuario1");
            System.out.println("   Contraseña: pass123");
            System.out.println("   Password Hash: " + passwordEncriptada);
        } else {
            System.out.println("ℹ️  Usuario 'usuario1' ya existe en la base de datos");
            System.out.println("   Password Hash actual: " + usuarioExistente.getPassword());

            // OPCIONAL: Actualizar contraseña si es necesario
            // Descomenta las siguientes líneas si quieres resetear la contraseña del usuario1
            /*
            String nuevaPassword = passwordEncoder.encode("pass123");
            usuarioExistente.setPassword(nuevaPassword);
            usuarioRepository.save(usuarioExistente);
            System.out.println("🔄 Contraseña de usuario1 actualizada");
            */
        }

        System.out.println("========================================");
        System.out.println("✅ DataInitializer completado");
        System.out.println("========================================");
    }
}