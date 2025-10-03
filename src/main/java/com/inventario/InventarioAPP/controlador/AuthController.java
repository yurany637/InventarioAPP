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
        String nombreUsuario = credenciales.get("nombreUsuario");
        String password = credenciales.get("password");

        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);

        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            return Collections.singletonMap("mensaje", "Login exitoso");
        } else {
            return Collections.singletonMap("error", "Usuario o contraseña inválidos");
        }
    }

    // ✅ REGISTRO
    @PostMapping("/registrar")
    public Map<String, String> registrarUsuario(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario()) != null) {
            return Collections.singletonMap("error", "El usuario ya existe");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol("USER");
        usuarioRepository.save(usuario);
        return Collections.singletonMap("mensaje", "Usuario registrado con éxito");
    }
}