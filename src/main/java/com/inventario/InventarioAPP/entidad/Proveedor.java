package com.inventario.InventarioAPP.entidad;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@Entity
@Table(name = "proveedores")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, message = "La dirección debe tener al menos 5 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*[0-9]).{5,}$",
            message = "La dirección debe contener letras y números"
    )
    private String direccion;

    @NotBlank(message = "El contacto es obligatorio")
    @Pattern(
            regexp = "^[0-9]{7,15}$",
            message = "El contacto debe tener entre 7 y 15 dígitos numéricos"
    )
    private String contacto;
}