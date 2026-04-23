package com.control.visitas.controllers;

import com.control.visitas.dtos.VisitDTO;
import com.control.visitas.models.enums.StateVisit;
import com.control.visitas.services.VisitService;
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
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisitController.class)
@AutoConfigureMockMvc(addFilters = false)
class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VisitService visitService;

    private VisitDTO testVisitDTO;

    @BeforeEach
    void setUp() {
        testVisitDTO = VisitDTO.builder()
                .id(1L)
                .visitDate(LocalDateTime.now().plusDays(2))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .description("Mantenimiento programada")
                .stateVisit(StateVisit.PROPOSAL)
                .installationsServicesId(1L)
                .technicalId(1L)
                .build();
    }

    @Test
    void findByInstallatioId_ShouldReturnVisitsList() throws Exception {
        // Given
        when(visitService.findVisitByInstallationId(1L))
                .thenReturn(List.of(testVisitDTO));

        // When & Then
        mockMvc.perform(get("/api/visit/findByInstallationId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("Mantenimiento programada"));
    }

    @Test
    void findByVisitId_ShouldReturnVisitDTO() throws Exception {
        // Given
        when(visitService.findByVisitId(1L))
                .thenReturn(testVisitDTO);

        // When & Then
        mockMvc.perform(get("/api/visit/findByVisitId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Mantenimiento programada"));
    }

    @Test
    void saveVisit_ShouldReturnCreated_WhenValidData() throws Exception {
        // Given
        testVisitDTO = VisitDTO.builder()
                .visitDate(LocalDateTime.now().plusDays(2))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .description("Mantenimiento programada")
                .stateVisit(StateVisit.PROPOSAL)
                .installationsServicesId(1L)
                .technicalId(1L)
                .build();

        when(visitService.saveVisit(any(VisitDTO.class)))
                .thenReturn(testVisitDTO);

        // When & Then
        mockMvc.perform(post("/api/visit/saveVisit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testVisitDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.technicalId").value(1L));
    }

    @Test
    void updateVisit_ShouldReturnOk_WhenValidData() throws Exception {
        // Given
        when(visitService.updateVisit(any(VisitDTO.class)))
                .thenReturn(testVisitDTO);

        // When & Then
        mockMvc.perform(put("/api/visit/updateVisit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testVisitDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteVisit_ShouldReturnOk_WhenVisitExists() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/visit/deleteVisit/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Visita Eliminada"))
                .andExpect(jsonPath("$.id").value("1"));
    }
}