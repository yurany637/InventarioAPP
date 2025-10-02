package com.inventario.InventarioAPP.controlador;

import com.inventario.InventarioAPP.entidad.Cliente;
import com.inventario.InventarioAPP.repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*") // Asegúrate de que coincida con la URL de tu frontend
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Cliente> obtenerPorId(@PathVariable Long id) {
        return clienteRepository.findById(id);
    }

    @PostMapping
    public Cliente crearCliente(@Valid @RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Long id, @Valid @RequestBody Cliente clienteActualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(clienteActualizado.getNombre());
                    cliente.setDireccion(clienteActualizado.getDireccion());
                    cliente.setContacto(clienteActualizado.getContacto());
                    return clienteRepository.save(cliente);
                })
                .orElseGet(() -> {
                    clienteActualizado.setId(id);
                    return clienteRepository.save(clienteActualizado);
                });
    }

    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }
}