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

    // ✅ LOGIN MEJORADO con validaciones
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credenciales) {
        Map<String, String> response = new HashMap<>();

        String username = credenciales.get("username");
        String password = credenciales.get("password");

        // Validar que los campos no estén vacíos
        if (username == null || username.trim().isEmpty()) {
            response.put("error", "El nombre de usuario es requerido");
            return ResponseEntity.badRequest().body(response);
        }

        if (password == null || password.trim().isEmpty()) {
            response.put("error", "La contraseña es requerida");
            return ResponseEntity.badRequest().body(response);
        }

        // Buscar usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username.trim());

        if (usuario == null) {
            response.put("error", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Verificar contraseña
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            response.put("error", "Contraseña incorrecta");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Login exitoso
        response.put("mensaje", "Login exitoso");
        response.put("username", usuario.getUsername());
        return ResponseEntity.ok(response);
    }

    // ✅ REGISTRO MEJORADO con validaciones
    @PostMapping("/registrar")
    public ResponseEntity<Map<String, String>> registrarUsuario(@RequestBody Usuario usuario) {
        Map<String, String> response = new HashMap<>();

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
            response.put("error", "El usuario ya existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Crear nuevo usuario
        usuario.setUsername(usuario.getUsername().trim());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);

        response.put("mensaje", "Usuario registrado con éxito");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}