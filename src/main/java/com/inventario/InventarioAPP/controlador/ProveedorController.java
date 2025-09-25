package com.inventario.InventarioAPP.controlador;

import com.inventario.InventarioAPP.entidad.Proveedor;
import com.inventario.InventarioAPP.repositorio.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "http://localhost:8081") // Asegúrate de que coincida con la URL de tu frontend
public class ProveedorController {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping
    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Proveedor> obtenerPorId(@PathVariable Long id) {
        return proveedorRepository.findById(id);
    }

    @PostMapping
    public Proveedor crearProveedor(@RequestBody Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @PutMapping("/{id}")
    public Proveedor actualizarProveedor(@PathVariable Long id, @RequestBody Proveedor proveedorActualizado) {
        return proveedorRepository.findById(id)
                .map(proveedor -> {
                    proveedor.setNombre(proveedorActualizado.getNombre());
                    proveedor.setDireccion(proveedorActualizado.getDireccion());
                    proveedor.setContacto(proveedorActualizado.getContacto());
                    return proveedorRepository.save(proveedor);
                })
                .orElseGet(() -> {
                    proveedorActualizado.setId(id);
                    return proveedorRepository.save(proveedorActualizado);
                });
    }

    @DeleteMapping("/{id}")
    public void eliminarProveedor(@PathVariable Long id) {
        proveedorRepository.deleteById(id);
    }
}
