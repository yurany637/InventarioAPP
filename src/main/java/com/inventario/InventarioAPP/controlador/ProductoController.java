package com.inventario.InventarioAPP.controlador;

import com.inventario.InventarioAPP.entidad.Producto;
import com.inventario.InventarioAPP.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:8081") // Permite la comunicación con el frontend
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;
    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }
    // Agrega métodos para actualizar y eliminar
}