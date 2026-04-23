package com.control.visitas.controllers;

import com.control.visitas.dtos.PagingDataDTO;
import com.control.visitas.dtos.machine.MachineDTO;
import com.control.visitas.dtos.machine.MachineResponseDTO;
import com.control.visitas.services.MachineService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MachineController.class)
@AutoConfigureMockMvc(addFilters = false)
class MachineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MachineService machineService;

    private MachineDTO testMachineDTO;
    private MachineResponseDTO testMachineResponseDTO;

    @BeforeEach
    void setUp() {
        testMachineDTO = MachineDTO.builder()
                .id(1L)
                .identifier(100)
                .serialNumber("SN123456")
                .brand("Dell")
                .model("PowerEdge")
                .customerId(1L)
                .typeMachineId(1L)
                .build();

        PagingDataDTO pagingDataDTO = new PagingDataDTO(1, 10, 1);
        testMachineResponseDTO = new MachineResponseDTO(List.of(testMachineDTO), pagingDataDTO);
    }

    @Test
    void findMachineByCustomer_ShouldReturnOk() throws Exception {
        // Given
        when(machineService.getAllMachineByCustomer(anyLong(), anyInt(), anyInt()))
                .thenReturn(testMachineResponseDTO);

        // When & Then
        mockMvc.perform(get("/api/machine/findMachineByCustomer")
                        .param("customerId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.machineDTOS").isArray())
                .andExpect(jsonPath("$.machineDTOS[0].identifier").value(100));
    }

    @Test
    void findMachineById_ShouldReturnMachineDTO() throws Exception {
        // Given
        when(machineService.findMachineById(1L))
                .thenReturn(testMachineDTO);

        // When & Then
        mockMvc.perform(get("/api/machine/findMachineById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.identifier").value(100));
    }

    @Test
    void saveMachine_ShouldReturnCreated() throws Exception {
        // Given
        testMachineDTO = MachineDTO.builder()
                .identifier(100)
                .serialNumber("SN123456")
                .brand("Dell")
                .model("PowerEdge")
                .customerId(1L)
                .typeMachineId(1L)
                .build();
        when(machineService.saveMachine(any(MachineDTO.class)))
                .thenReturn(testMachineDTO);

        // When & Then
        mockMvc.perform(post("/api/machine/saveMachine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMachineDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateMachine_ShouldReturnOk() throws Exception {
        // Given
        when(machineService.updateMachine(any(MachineDTO.class)))
                .thenReturn(testMachineDTO);

        // When & Then
        mockMvc.perform(put("/api/machine/updateMachine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMachineDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteMachine_ShouldReturnOk() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/machine/deleteMachine/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.message").value("Maquina Eliminada"));
    }
}