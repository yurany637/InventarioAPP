package com.inventario.InventarioAPP.entidad;

import lombok.Data;
import jakarta.persistence.*;
@Data
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int cantidad;
}
