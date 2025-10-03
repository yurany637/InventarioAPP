package com.inventario.InventarioAPP.repositorio;

import com.inventario.InventarioAPP.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // ✅ Cambiado a "username" para que coincida con el campo en la entidad Usuario
    Usuario findByUsername(String username);
}