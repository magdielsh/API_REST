package com.control.visitas.controllers;

import com.control.visitas.dtos.PagingDataDTO;
import com.control.visitas.dtos.installation_service.InstallationServiceDTO;
import com.control.visitas.dtos.installation_service.InstallationServiceResponseDTO;
import com.control.visitas.services.InstallationServiceService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InstallationServiceController.class)
@AutoConfigureMockMvc(addFilters = false)
class InstallationServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InstallationServiceService installationServiceService;

    private InstallationServiceDTO testInstallationDTO;
    private InstallationServiceResponseDTO testResponseDTO;

    @BeforeEach
    void setUp() {
        testInstallationDTO = InstallationServiceDTO.builder()
                .id(1L)
                .codeInstallation("INST001")
                .startDate(LocalDateTime.now().plusDays(2))
                .endDate(LocalDateTime.now().plusMonths(5))
                .quantityEquipments(5)
                .typeServicesId(1L)
                .customerId(1L)
                .technicalId(1L)
                .machines(List.of())
                .build();

        PagingDataDTO pagingDataDTO = new PagingDataDTO(1, 10, 1);
        testResponseDTO = new InstallationServiceResponseDTO(List.of(testInstallationDTO), pagingDataDTO);
    }

    @Test
    void findAllInstallationService_ShouldReturnOk() throws Exception {
        // Given
        when(installationServiceService.findAllInstallationService(anyInt(), anyInt()))
                .thenReturn(testResponseDTO);

        // When & Then
        mockMvc.perform(get("/api/installationService/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.installationService").isArray())
                .andExpect(jsonPath("$.installationService[0].codeInstallation").value("INST001"));
    }

    @Test
    void findSilter_ShouldReturnOk() throws Exception {
        // Given
        when(installationServiceService.findInstallationByCustomerId(anyString(), anyInt(), anyInt()))
                .thenReturn(testResponseDTO);

        // When & Then
        mockMvc.perform(get("/api/installationService/findInstallationServiceFilter")
                        .param("search", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.installationService").isArray());
    }

    @Test
    void findByCodeInstallation_ShouldReturnInstallationServiceDTO() throws Exception {
        // Given
        when(installationServiceService.findInstallationServiceByCode("INST001"))
                .thenReturn(testInstallationDTO);

        // When & Then
        mockMvc.perform(get("/api/installationService/findByCodeInstallation/INST001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codeInstallation").value("INST001"));
    }

    @Test
    void saveInstallationService_ShouldReturnCreated() throws Exception {
        // Given
        testInstallationDTO = InstallationServiceDTO.builder()
                .codeInstallation("INST001")
                .startDate(LocalDateTime.now().plusDays(2))
                .endDate(LocalDateTime.now().plusMonths(5))
                .quantityEquipments(5)
                .typeServicesId(1L)
                .customerId(1L)
                .technicalId(1L)
                .machines(List.of())
                .build();

        when(installationServiceService.saveInstallationService(any(InstallationServiceDTO.class)))
                .thenReturn(testInstallationDTO);

        // When & Then
        mockMvc.perform(post("/api/installationService/saveInstallationService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testInstallationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codeInstallation").value("INST001"));
    }

    @Test
    void updateInstallationService_ShouldReturnOk() throws Exception {
        // Given
        when(installationServiceService.updateInstallationService(any(InstallationServiceDTO.class)))
                .thenReturn(testInstallationDTO);

        // When & Then
        mockMvc.perform(put("/api/installationService/updateInstallationService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testInstallationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codeInstallation").value("INST001"));
    }

    @Test
    void deleteInstallationService_ShouldReturnOk() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/installationService/deleteInstallationService/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Instalación Eliminada"))
                .andExpect(jsonPath("$.id").value("1"));
    }
}