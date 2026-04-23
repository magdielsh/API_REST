package com.control.visitas.services;

import com.control.visitas.dtos.IncidenceDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.Incidence;
import com.control.visitas.models.entities.InstallationsServices;
import com.control.visitas.models.enums.IncidentType;
import com.control.visitas.repository.IncidenceRepository;
import com.control.visitas.repository.InstallationsServicesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidenceServiceTest {

    @Mock
    private IncidenceRepository incidenceRepository;

    @Mock
    private InstallationsServicesRepository installationsServicesRepository;

    @InjectMocks
    private IncidenceService incidenceService;

    private Incidence testIncidence;
    private IncidenceDTO testIncidenceDTO;
    private InstallationsServices testInstallation;

    @BeforeEach
    void setUp() {
        testInstallation = InstallationsServices.builder()
                .id(1L)
                .codeInstallation("INST001")
                .startDate(LocalDateTime.now())
                .quantityEquipments(5)
                .build();

        testIncidence = Incidence.builder()
                .id(1L)
                .incidenceCode("INC001")
                .incidentType(IncidentType.TECHNICAL_VISIT)
                .description("Fallo en servidor")
                .openingDate(LocalDateTime.now())
                .closingDate(LocalDateTime.now().plusDays(1))
                .isOperational(true)
                .incidenceSolution("")
                .closedBy("")
                .installationsServices(testInstallation)
                .build();

        testIncidenceDTO = IncidenceDTO.builder()
                .id(1L)
                .incidenceCode("INC001")
                .incidentType(IncidentType.TECHNICAL_VISIT)
                .description("Fallo en servidor")
                .openingDate(LocalDateTime.now())
                .closingDate(LocalDateTime.now().plusDays(1))
                .isOperational(true)
                .incidenceSolution("")
                .closedBy("")
                .installationServiceId(1L)
                .build();
    }

    @Test
    void findIncidenceByInstallation_ShouldReturnIncidenceList_WhenInstallationExists() {
        // Given
        when(incidenceRepository.findIncidenceByInstallationsServicesId(1L))
                .thenReturn(List.of(testIncidence));

        // When
        List<IncidenceDTO> result = incidenceService.findIncidenceByInstallation(1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("INC001", result.get(0).getIncidenceCode());
        verify(incidenceRepository, times(1)).findIncidenceByInstallationsServicesId(1L);
    }

    @Test
    void findIncidenceByInstallation_ShouldReturnEmptyList_WhenNoIncidences() {
        // Given
        when(incidenceRepository.findIncidenceByInstallationsServicesId(999L))
                .thenReturn(List.of());

        // When
        List<IncidenceDTO> result = incidenceService.findIncidenceByInstallation(999L);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(incidenceRepository, times(1)).findIncidenceByInstallationsServicesId(999L);
    }

    @Test
    void findIncidenceById_ShouldReturnIncidenceDTO_WhenIncidenceExists() {
        // Given
        when(incidenceRepository.findById(1L))
                .thenReturn(Optional.of(testIncidence));

        // When
        IncidenceDTO result = incidenceService.findIncidenceById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("INC001", result.getIncidenceCode());
        verify(incidenceRepository, times(1)).findById(1L);
    }

    @Test
    void saveIncidence_ShouldReturnSavedIncidenceDTO_WhenValidData() {
        // Given
        IncidenceDTO newIncidenceDTO = IncidenceDTO.builder()
                .incidenceCode("INC002")
                .incidentType(IncidentType.COMMUNICATION)
                .description("Nueva incidencia")
                .openingDate(LocalDateTime.now())
                .closingDate(LocalDateTime.now().plusDays(1))
                .isOperational(false)
                .installationServiceId(1L)
                .build();

        when(installationsServicesRepository.getReferenceById(1L))
                .thenReturn(testInstallation);
        when(incidenceRepository.save(any(Incidence.class)))
                .thenReturn(testIncidence);

        // When
        IncidenceDTO result = incidenceService.saveIncidence(newIncidenceDTO);

        // Then
        assertNotNull(result);
        verify(incidenceRepository, times(1)).save(any(Incidence.class));
        verify(installationsServicesRepository, times(1)).getReferenceById(1L);
    }

    @Test
    void updateIncidence_ShouldReturnUpdatedIncidenceDTO_WhenIncidenceExists() {
        // Given
        IncidenceDTO updateDTO = IncidenceDTO.builder()
                .id(1L)
                .incidenceCode("INC001")
                .incidentType(IncidentType.RECOMMENDATION)
                .description("Descripción actualizada")
                .openingDate(LocalDateTime.now())
                .closingDate(LocalDateTime.now().plusDays(1))
                .isOperational(true)
                .incidenceSolution("Solución aplicada")
                .closedBy("Técnico Juan")
                .installationServiceId(1L)
                .build();

        when(incidenceRepository.findById(1L))
                .thenReturn(Optional.of(testIncidence));

        // When
        IncidenceDTO result = incidenceService.updateIncidence(updateDTO);

        // Then
        assertNotNull(result);
        verify(incidenceRepository, times(1)).findById(1L);
    }

    @Test
    void deleteIncidence_ShouldCallDeleteRepository_WhenIncidenceExists() {
        // Given
        when(incidenceRepository.existsById(1L))
                .thenReturn(true);

        // When
        incidenceService.deleteIncidence(1L);

        // Then
        verify(incidenceRepository, times(1)).existsById(1L);
        verify(incidenceRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteIncidence_ShouldThrowException_WhenIncidenceNotExists() {
        // Given
        when(incidenceRepository.existsById(999L))
                .thenReturn(false);

        // When & Then
        assertThrows(ResourseNotFoundException.class, () -> {
            incidenceService.deleteIncidence(999L);
        });

        verify(incidenceRepository, times(1)).existsById(999L);
        verify(incidenceRepository, never()).deleteById(anyLong());
    }
}