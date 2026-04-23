package com.control.visitas.controllers;

import com.control.visitas.dtos.TypeServiceDTO;
import com.control.visitas.services.TypeServiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TypeServiceController.class)
@AutoConfigureMockMvc(addFilters = false)
class TypeServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TypeServiceService typeServiceService;

    private TypeServiceDTO testTypeServiceDTO;

    @BeforeEach
    void setUp() {
        testTypeServiceDTO = TypeServiceDTO.builder()
                .id(1L)
                .typeService("Mantenimiento")
                .build();
    }

    @Test
    void findAllTypeService_ShouldReturnOk() throws Exception {
        // Given
        when(typeServiceService.findAllTypeServices())
                .thenReturn(List.of(testTypeServiceDTO));

        // When & Then
        mockMvc.perform(get("/api/typeService/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].typeService").value("Mantenimiento"));
    }

    @Test
    void saveTypeService_ShouldReturnCreated() throws Exception {
        // Given
        testTypeServiceDTO = TypeServiceDTO.builder()
                .typeService("Mantenimiento")
                .build();
        when(typeServiceService.saveTypeService(any(TypeServiceDTO.class)))
                .thenReturn(testTypeServiceDTO);

        // When & Then
        mockMvc.perform(post("/api/typeService/saveTypeService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTypeServiceDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.typeService").value("Mantenimiento"));
    }

    @Test
    void updateTypeService_ShouldReturnOk() throws Exception {
        // Given
        when(typeServiceService.updateTypeService(any(TypeServiceDTO.class)))
                .thenReturn(testTypeServiceDTO);

        // When & Then
        mockMvc.perform(put("/api/typeService/updateTypeService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTypeServiceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteTypeService_ShouldReturnOk() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/typeService/deleteTypeService/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Tipo de Servicio Eliminado"))
                .andExpect(jsonPath("$.id").value("1"));
    }
}