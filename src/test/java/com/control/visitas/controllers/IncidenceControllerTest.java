package com.control.visitas.controllers;

import com.control.visitas.dtos.IncidenceDTO;
import com.control.visitas.models.enums.IncidentType;
import com.control.visitas.services.IncidenceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IncidenceController.class)
@AutoConfigureMockMvc(addFilters = false)
class IncidenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IncidenceService incidenceService;

    private IncidenceDTO testIncidenceDTO;

    @BeforeEach
    void setUp() {
        testIncidenceDTO = IncidenceDTO.builder()
                .id(1L)
                .incidenceCode("INC001")
                .incidentType(IncidentType.TECHNICAL_VISIT)
                .description("Fallo en servidor")
                .openingDate(LocalDateTime.now().plusDays(2))
                .closingDate(LocalDateTime.now().plusDays(5))
                .isOperational(true)
                .installationServiceId(1L)
                .build();
    }

    @Test
    void findAllByInstallationId_ShouldReturnIncidencesList() throws Exception {
        // Given
        when(incidenceService.findIncidenceByInstallation(1L))
                .thenReturn(List.of(testIncidenceDTO));

        // When & Then
        mockMvc.perform(get("/api/incidence/findByInstallationId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].incidenceCode").value("INC001"));
    }

    @Test
    void findByIncidenceId_ShouldReturnIncidenceDTO() throws Exception {
        // Given
        when(incidenceService.findIncidenceById(1L))
                .thenReturn(testIncidenceDTO);

        // When & Then
        mockMvc.perform(get("/api/incidence/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.incidenceCode").value("INC001"));
    }

    @Test
    void saveIncidence_ShouldReturnCreated_WhenValidData() throws Exception {
        // Given
        testIncidenceDTO = IncidenceDTO.builder()
                .incidenceCode("INC001")
                .incidentType(IncidentType.TECHNICAL_VISIT)
                .description("Fallo en servidor")
                .openingDate(LocalDateTime.now().plusDays(2))
                .closingDate(LocalDateTime.now().plusDays(5))
                .isOperational(true)
                .installationServiceId(1L)
                .build();

        when(incidenceService.saveIncidence(any(IncidenceDTO.class)))
                .thenReturn(testIncidenceDTO);

        // When & Then
        mockMvc.perform(post("/api/incidence/saveIncidence")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testIncidenceDTO)))
                .andExpect(status().isCreated());
                //.andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateIncidence_ShouldReturnOk_WhenValidData() throws Exception {
        // Given
        when(incidenceService.updateIncidence(any(IncidenceDTO.class)))
                .thenReturn(testIncidenceDTO);

        // When & Then
        mockMvc.perform(put("/api/incidence/updateIncidence")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testIncidenceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteIncidence_ShouldReturnOk_WhenIncidenceExists() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/incidence/deleteIncidence/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Incidencia Eliminada"))
                .andExpect(jsonPath("$.id").value("1"));
    }
}