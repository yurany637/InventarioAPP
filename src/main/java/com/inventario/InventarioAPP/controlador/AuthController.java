package com.inventario.InventarioAPP.controlador;

import com.inventario.InventarioAPP.entidad.Usuario;
import com.inventario.InventarioAPP.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ LOGIN con LOGS detallados para debugging
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credenciales) {
        Map<String, String> response = new HashMap<>();

        String username = credenciales.get("username");
        String password = credenciales.get("password");

        System.out.println("========================================");
        System.out.println("🔐 Intento de login");
        System.out.println("Usuario recibido: " + username);
        System.out.println("Password recibido: " + password);

        // Validar que los campos no estén vacíos
        if (username == null || username.trim().isEmpty()) {
            System.out.println("❌ Error: Username vacío");
            response.put("error", "El nombre de usuario es requerido");
            return ResponseEntity.badRequest().body(response);
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println("❌ Error: Password vacío");
            response.put("error", "La contraseña es requerida");
            return ResponseEntity.badRequest().body(response);
        }

        // Buscar usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username.trim());

        if (usuario == null) {
            System.out.println("❌ Usuario NO encontrado en la base de datos");
            response.put("error", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        System.out.println("✅ Usuario encontrado en BD");
        System.out.println("Username en BD: " + usuario.getUsername());
        System.out.println("Password Hash en BD: " + usuario.getPassword());

        // Generar hash de la password recibida para comparar
        String hashPasswordRecibida = passwordEncoder.encode(password);
        System.out.println("Hash de password recibida: " + hashPasswordRecibida);

        // Verificar contraseña
        boolean passwordMatch = passwordEncoder.matches(password, usuario.getPassword());
        System.out.println("¿Passwords coinciden?: " + passwordMatch);

        if (!passwordMatch) {
            System.out.println("❌ Contraseña incorrecta");
            System.out.println("========================================");
            response.put("error", "Contraseña incorrecta");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Login exitoso
        System.out.println("✅ Login EXITOSO");
        System.out.println("========================================");
        response.put("mensaje", "Login exitoso");
        response.put("username", usuario.getUsername());
        return ResponseEntity.ok(response);
    }

    // ✅ REGISTRO con validaciones
    @PostMapping("/registrar")
    public ResponseEntity<Map<String, String>> registrarUsuario(@RequestBody Usuario usuario) {
        Map<String, String> response = new HashMap<>();

        System.out.println("========================================");
        System.out.println("📝 Intento de registro");
        System.out.println("Usuario: " + usuario.getUsername());

        // Validar campos
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            response.put("error", "El nombre de usuario es requerido");
            return ResponseEntity.badRequest().body(response);
        }

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            response.put("error", "La contraseña es requerida");
            return ResponseEntity.badRequest().body(response);
        }

        if (usuario.getUsername().length() < 3) {
            response.put("error", "El nombre de usuario debe tener al menos 3 caracteres");
            return ResponseEntity.badRequest().body(response);
        }

        if (usuario.getPassword().length() < 4) {
            response.put("error", "La contraseña debe tener al menos 4 caracteres");
            return ResponseEntity.badRequest().body(response);
        }

        // Verificar si el usuario ya existe
        if (usuarioRepository.findByUsername(usuario.getUsername().trim()) != null) {
            System.out.println("❌ Usuario ya existe");
            response.put("error", "El usuario ya existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Crear nuevo usuario
        usuario.setUsername(usuario.getUsername().trim());
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        usuarioRepository.save(usuario);

        System.out.println("✅ Usuario registrado exitosamente");
        System.out.println("Password Hash: " + passwordEncriptada);
        System.out.println("========================================");

        response.put("mensaje", "Usuario registrado con éxito");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}