package com.inventario.InventarioAPP.controlador;

import com.inventario.InventarioAPP.entidad.Producto;
import com.inventario.InventarioAPP.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") // Permite todos los orígenes para desarrollo/producción
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Producto> obtenerPorId(@PathVariable Long id) {
        return productoRepository.findById(id);
    }

    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoActualizado.getNombre());
                    producto.setCantidad(productoActualizado.getCantidad());
                    return productoRepository.save(producto);
                })
                .orElseGet(() -> {
                    productoActualizado.setId(id);
                    return productoRepository.save(productoActualizado);
                });
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
    }
}