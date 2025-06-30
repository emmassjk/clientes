package com.clientes.servicies;

import com.clientes.dto.ClienteDTO;
import com.clientes.models.ClienteModels;
import com.clientes.repositories.ClienteRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServicies {

    @Autowired
    private ClienteRepositories clienteRepositories;

    public ClienteDTO guardar(ClienteDTO dto) {
        ClienteModels cliente = toEntity(dto);
        ClienteModels saved = clienteRepositories.save(cliente);
        return toDTO(saved);
    }

    public List<ClienteDTO> listar() {
        return clienteRepositories.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Cambiado a Integer para coincidir con el repositorio
    public Optional<ClienteDTO> obtenerPorId(Integer id) {
        return clienteRepositories.findById(id)
                .map(this::toDTO);
    }

    public Optional<ClienteDTO> actualizar(Integer id, ClienteDTO dto) {
        return clienteRepositories.findById(id).map(cliente -> {
            cliente.setIdUsuario(dto.getIdUsuario());
            cliente.setNombreCompleto(dto.getNombreCompleto());
            cliente.setRut(dto.getRut());
            cliente.setDireccion(dto.getDireccion());
            cliente.setTelefono(dto.getTelefono());
            return toDTO(clienteRepositories.save(cliente));
        });
    }

    public boolean eliminar(Integer id) {
        if (clienteRepositories.existsById(id)) {
            clienteRepositories.deleteById(id);
            return true;
        }
        return false;
    }

    // Métodos auxiliares
    private ClienteDTO toDTO(ClienteModels cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setIdUsuario(cliente.getIdUsuario());
        dto.setNombreCompleto(cliente.getNombreCompleto());
        dto.setRut(cliente.getRut());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }

    private ClienteModels toEntity(ClienteDTO dto) {
        ClienteModels cliente = new ClienteModels();
        cliente.setIdCliente(dto.getIdCliente());
        cliente.setIdUsuario(dto.getIdUsuario());
        cliente.setNombreCompleto(dto.getNombreCompleto());
        cliente.setRut(dto.getRut());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        return cliente;
    }
}
