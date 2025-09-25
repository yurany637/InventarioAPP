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
@CrossOrigin(origins = "http://localhost:8081") // Asegúrate de que coincida con la URL de tu frontend
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credenciales) {
        String nombreUsuario = credenciales.get("nombreUsuario");
        String password = credenciales.get("password");

        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);

        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            // En un proyecto real, aquí se generaría y retornaría un token JWT
            return Collections.singletonMap("mensaje", "Login exitoso");
        } else {
            return Collections.singletonMap("error", "Credenciales inválidas");
        }
    }

    @PostMapping("/registrar")
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
        // Encripta la contraseña antes de guardarla
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol("USER"); // Asigna un rol predeterminado
        return usuarioRepository.save(usuario);
    }
}
