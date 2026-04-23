package com.control.visitas.controllers;

import com.control.visitas.dtos.PagingDataDTO;
import com.control.visitas.dtos.technical.TechnicalDTO;
import com.control.visitas.dtos.technical.TechnicalRequestDTO;
import com.control.visitas.dtos.technical.TechnicalResponseDTO;
import com.control.visitas.services.TechnicalService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TechnicalController.class)
@AutoConfigureMockMvc(addFilters = false)
class TechnicalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TechnicalService technicalService;

    private TechnicalDTO testTechnicalDTO;
    private TechnicalRequestDTO testTechnicalRequestDTO;
    private TechnicalResponseDTO testResponseDTO;

    @BeforeEach
    void setUp() {
        testTechnicalDTO = new TechnicalDTO(1L, "Juan Perez", "juan@test.com", "123456789", 
                "Calle 123", "Buenos Aires", "Capital", "-34.60,-58.38");

        testTechnicalRequestDTO = TechnicalRequestDTO.builder()
                .id(1L)
                .name("Juan Perez")
                .email("juan@test.com")
                .mobilePhone("123456789")
                .address("Calle 123")
                .province("Buenos Aires")
                .locality("Capital")
                .coordinates("-34.60,-58.38")
                .build();

        PagingDataDTO pagingDataDTO = new PagingDataDTO(1, 10, 1);
        testResponseDTO = new TechnicalResponseDTO(List.of(testTechnicalDTO), pagingDataDTO);
    }

    @Test
    void findAll_ShouldReturnOk() throws Exception {
        // Given
        when(technicalService.findAllTechnical(anyInt(), anyInt()))
                .thenReturn(testResponseDTO);

        // When & Then
        mockMvc.perform(get("/api/technical/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.technical").isArray())
                .andExpect(jsonPath("$.technical[0].name").value("Juan Perez"));
    }

    @Test
    void findFilterTechnical_ShouldReturnOk() throws Exception {
        // Given
        when(technicalService.findFilterTechnical(anyString(), anyInt(), anyInt()))
                .thenReturn(testResponseDTO);

        // When & Then
        mockMvc.perform(get("/api/technical/findFilterTechnical")
                        .param("search", "Juan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.technical").isArray());
    }

    @Test
    void findTechnicalById_ShouldReturnTechnicalDTO() throws Exception {
        // Given
        when(technicalService.findTechnicalById(1L))
                .thenReturn(testTechnicalDTO);

        // When & Then
        mockMvc.perform(get("/api/technical/find/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Perez"));
    }

    @Test
    void saveTechnical_ShouldReturnCreated() throws Exception {
        // Given
        testTechnicalRequestDTO = TechnicalRequestDTO.builder()
                .name("Juan Perez")
                .email("juan@test.com")
                .mobilePhone("123456789")
                .address("Calle 123")
                .province("Buenos Aires")
                .locality("Capital")
                .coordinates("-34.60,-58.38")
                .build();

        when(technicalService.saveTechnical(any(TechnicalRequestDTO.class)))
                .thenReturn(testTechnicalDTO);

        // When & Then
        mockMvc.perform(post("/api/technical/saveTechnical")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTechnicalRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Juan Perez"));
    }

    @Test
    void updateTechnical_ShouldReturnOk() throws Exception {
        // Given
        when(technicalService.updateTechnical(any(TechnicalRequestDTO.class)))
                .thenReturn(testTechnicalDTO);

        // When & Then
        mockMvc.perform(put("/api/technical/updateTechnical")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTechnicalRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Perez"));
    }

    @Test
    void deleteTechnical_ShouldReturnOk() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/technical/deleteTechnical/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Técnico eliminado exitosamente"))
                .andExpect(jsonPath("$.id").value("1"));
    }
}