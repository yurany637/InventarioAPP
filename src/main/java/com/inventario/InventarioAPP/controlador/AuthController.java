package com.inventario.InventarioAPP.controlador;

import com.inventario.InventarioAPP.entidad.Usuario;
import com.inventario.InventarioAPP.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ LOGIN
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credenciales) {
        String username = credenciales.get("username"); // cambiado
        String password = credenciales.get("password");

        Usuario usuario = usuarioRepository.findByUsername(username); // cambiado

        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            return Collections.singletonMap("mensaje", "Login exitoso");
        } else {
            return Collections.singletonMap("error", "Usuario o contraseña inválidos");
        }
    }

    // ✅ REGISTRO
    @PostMapping("/registrar")
    public Map<String, String> registrarUsuario(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()) != null) { // cambiado
            return Collections.singletonMap("error", "El usuario ya existe");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // encode de la contraseña
        usuarioRepository.save(usuario); // no más rol
        return Collections.singletonMap("mensaje", "Usuario registrado con éxito");
    }
}