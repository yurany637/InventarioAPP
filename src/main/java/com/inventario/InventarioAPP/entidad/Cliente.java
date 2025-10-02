package com.inventario.InventarioAPP.entidad;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9\\s#.,-]+$",
            message = "La dirección debe contener letras y números")
    private String direccion;

    @NotBlank(message = "El contacto no puede estar vacío")
    @Pattern(regexp = "^[0-9]{7,15}$",
            message = "El contacto debe contener solo números y tener entre 7 y 15 dígitos")
    private String contacto;
}