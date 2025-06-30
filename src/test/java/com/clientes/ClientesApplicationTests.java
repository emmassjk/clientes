package com.clientes;

import com.clientes.dto.ClienteDTO;
import com.clientes.models.ClienteModels;
import com.clientes.repositories.ClienteRepositories;
import com.clientes.servicies.ClienteServicies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientesApplicationTests {

    @Mock
    private ClienteRepositories clienteRepositories;

    @InjectMocks
    private ClienteServicies clienteServicies;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerPorId() {
        try {
            Integer idCliente1 = 1;
            Integer idUsuario = 10;

            ClienteModels mockCliente = new ClienteModels();
            mockCliente.setIdCliente(idCliente1);
            mockCliente.setIdUsuario(idUsuario);
            mockCliente.setNombreCompleto("Juan Pérez");
            mockCliente.setRut("12345678-9");
            mockCliente.setDireccion("Calle Falsa 123");
            mockCliente.setTelefono("987654321");

            when(clienteRepositories.findById(idCliente1)).thenReturn(Optional.of(mockCliente));

            ClienteDTO result = clienteServicies.obtenerPorId(idCliente1).orElse(null);

            // Debug: imprimir valores
            System.out.println("Resultado del cliente:");
            System.out.println("ID Cliente: " + result.getIdCliente());
            System.out.println("ID Usuario: " + result.getIdUsuario());
            System.out.println("Nombre: " + result.getNombreCompleto());
            System.out.println("Rut: " + result.getRut());
            System.out.println("Dirección: " + result.getDireccion());
            System.out.println("Teléfono: " + result.getTelefono());

            // Aserciones corregidas
            assertNotNull(result);
            assertEquals("Juan Pérez", result.getNombreCompleto());
            assertEquals(idCliente1, result.getIdCliente());
            assertEquals(idUsuario, result.getIdUsuario());
            assertEquals("12345678-9", result.getRut());
            assertEquals("Calle Falsa 123", result.getDireccion());
            assertEquals("987654321", result.getTelefono());

            verify(clienteRepositories).findById(idCliente1);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Se produjo una excepción inesperada: " + e.getMessage());
        }
    }
}
