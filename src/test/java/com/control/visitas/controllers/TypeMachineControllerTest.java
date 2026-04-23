package com.control.visitas.controllers;

import com.control.visitas.dtos.TypeMachineDTO;
import com.control.visitas.services.TypeMachineService;
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

@WebMvcTest(TypeMachineController.class)
@AutoConfigureMockMvc(addFilters = false)
class TypeMachineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TypeMachineService typeMachineService;

    private TypeMachineDTO testTypeMachineDTO;

    @BeforeEach
    void setUp() {
        testTypeMachineDTO = TypeMachineDTO.builder()
                .id(1L)
                .typeMachine("Servidor")
                .build();
    }

    @Test
    void findAll_ShouldReturnOk() throws Exception {
        // Given
        when(typeMachineService.getAllTypeMachine())
                .thenReturn(List.of(testTypeMachineDTO));

        // When & Then
        mockMvc.perform(get("/api/typeMachine/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].typeMachine").value("Servidor"));
    }

    @Test
    void saveTypeMachine_ShouldReturnCreated() throws Exception {
        // Given
        testTypeMachineDTO = TypeMachineDTO.builder()
                .typeMachine("Servidor")
                .build();

        when(typeMachineService.saveTypeMachine(any(TypeMachineDTO.class)))
                .thenReturn(testTypeMachineDTO);

        // When & Then
        mockMvc.perform(post("/api/typeMachine/saveTypeMachine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTypeMachineDTO)))
                .andExpect(status().isCreated());
                //.andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateTypeMachine_ShouldReturnOk() throws Exception {
        // Given
        when(typeMachineService.updateTypeMachine(any(TypeMachineDTO.class)))
                .thenReturn(testTypeMachineDTO);

        // When & Then
        mockMvc.perform(put("/api/typeMachine/updateTypeMachine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTypeMachineDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteTypeMachine_ShouldReturnOk() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/typeMachine/deleteTypeMachine/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Tipo de maquina eliminada exitosamente"))
                .andExpect(jsonPath("$.id").value("1"));
    }
}