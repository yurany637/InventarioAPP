package com.inventario.InventarioAPP.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF para la API REST
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilita CORS
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll() // Permite acceso público a las URLs de autenticación
                        .requestMatchers("/actuator/health").permitAll() // Para health checks de Render
                        .requestMatchers("/api/**").permitAll() // Permite acceso a todos los endpoints de la API
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra petición
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ✅ ACTUALIZADO: Permitir tanto localhost como tu dominio de Render
        configuration.setAllowedOriginPatterns(Arrays.asList(
                "https://inventario-frontend-l0sb.onrender.com", // Tu frontend en Render
                "http://localhost:8081", // Para desarrollo local
                "http://localhost:8080", // Para desarrollo local del backend
                "https://*.onrender.com", // Cualquier subdominio de Render
                "*" // Para desarrollo (remover en producción si quieres más seguridad)
        ));

        // ✅ ACTUALIZADO: Incluir todos los métodos HTTP necesarios
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));

        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        // ✅ NUEVO: Permitir que el navegador cache las opciones preflight
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}