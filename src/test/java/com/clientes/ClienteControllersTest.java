package com.clientes;

import com.clientes.controllers.ClienteControllers;
import com.clientes.dto.ClienteDTO;
import com.clientes.servicies.ClienteServicies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteControllers.class)
public class ClienteControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteServicies clienteServicies;

    private ClienteDTO mockDto;

    @BeforeEach
    void setUp() {
        mockDto = new ClienteDTO();
        mockDto.setIdCliente(1);
        mockDto.setNombreCompleto("Juan Pérez");
    }

    @Test
    void testObtenerPorId() throws Exception {
        when(clienteServicies.obtenerPorId(1)).thenReturn(Optional.of(mockDto));

        mockMvc.perform(get("/api/clientes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Pérez"));
    }
}
