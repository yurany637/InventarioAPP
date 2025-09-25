package com.inventario.InventarioAPP.entidad;

import lombok.Data;
import jakarta.persistence.*;
@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreUsuario;
    private String password;
    private String rol; // ADMIN o USER
}
