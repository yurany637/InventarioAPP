package com.inventario.InventarioAPP.entidad;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "proveedores")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String direccion;
    private String contacto;
}