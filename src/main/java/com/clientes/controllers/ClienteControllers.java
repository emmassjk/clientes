package com.clientes.controllers;

import com.clientes.dto.ClienteDTO;
import com.clientes.servicies.ClienteServicies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteControllers {

    @Autowired
    private ClienteServicies clienteServicies;

    @PostMapping
    public ResponseEntity<ClienteDTO> crear(@RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(clienteServicies.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok(clienteServicies.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtener(@PathVariable Integer id) {
        return clienteServicies.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable Integer id, @RequestBody ClienteDTO dto) {
        return clienteServicies.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return clienteServicies.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // METODO HATEOAS para buscar por ID
    @GetMapping("/hateoas/{id}")
    public ClienteDTO obtenerHATEOAS(@PathVariable Integer id) {
        ClienteDTO dto = clienteServicies.obtenerPorId(id).orElse(null);
        if (dto == null) {
            return null;
        }

        // links urls de la misma API
        dto.add(linkTo(methodOn(ClienteControllers.class).obtenerHATEOAS(id)).withSelfRel());
        dto.add(linkTo(methodOn(ClienteControllers.class).obtenerTodosHATEOAS()).withRel("todos"));
        dto.add(linkTo(methodOn(ClienteControllers.class).eliminar(id)).withRel("eliminar"));

        // links HATEOAS para API Gateway "A mano"
        dto.add(Link.of("http://localhost:8888/api/proxy/clientes/" + dto.getIdCliente()).withSelfRel());
        dto.add(Link.of("http://localhost:8888/api/proxy/clientes/" + dto.getIdCliente()).withRel("Modificar HATEOAS").withType("PUT"));
        dto.add(Link.of("http://localhost:8888/api/proxy/clientes/" + dto.getIdCliente()).withRel("Eliminar HATEOAS").withType("DELETE"));

        return dto;
    }

    // METODO HATEOAS para listar todos los clientes utilizando HATEOAS
    @GetMapping("/hateoas")
    public List<ClienteDTO> obtenerTodosHATEOAS() {
        List<ClienteDTO> lista = clienteServicies.listar();

        for (ClienteDTO dto : lista) {
            // link url de la misma API
            dto.add(linkTo(methodOn(ClienteControllers.class).obtenerHATEOAS(dto.getIdCliente())).withSelfRel());

            // links HATEOAS para API Gateway "A mano"
            dto.add(Link.of("http://localhost:8888/api/proxy/clientes").withRel("Get todos HATEOAS"));
            dto.add(Link.of("http://localhost:8888/api/proxy/clientes/" + dto.getIdCliente()).withRel("Crear HATEOAS").withType("POST"));
        }

        return lista;
    }

}
