package com.control.visitas.services;

import com.control.visitas.dtos.VisitDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.InstallationsServices;
import com.control.visitas.models.entities.Technical;
import com.control.visitas.models.entities.Visits;
import com.control.visitas.models.enums.StateVisit;
import com.control.visitas.repository.InstallationsServicesRepository;
import com.control.visitas.repository.TechnicalRepository;
import com.control.visitas.repository.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private InstallationsServicesRepository installationsServicesRepository;

    @Mock
    private TechnicalRepository technicalRepository;

    @InjectMocks
    private VisitService visitService;

    private Visits testVisit;
    private VisitDTO testVisitDTO;
    private InstallationsServices testInstallation;
    private Technical testTechnical;

    @BeforeEach
    void setUp() {
        testTechnical = Technical.builder()
                .id(1L)
                .name("Juan Perez")
                .email("juan@test.com")
                .mobilePhone("123456789")
                .address("Calle 123")
                .province("Buenos Aires")
                .locality("Capital")
                .coordinates("-34.60,-58.38")
                .build();

        testInstallation = InstallationsServices.builder()
                .id(1L)
                .codeInstallation("INST001")
                .startDate(LocalDateTime.now())
                .quantityEquipments(5)
                .typeServices(new com.control.visitas.models.entities.Type_Services())
                .customers(new com.control.visitas.models.entities.Customers())
                .technical(testTechnical)
                .build();

        testVisit = Visits.builder()
                .id(1L)
                .visitDate(LocalDateTime.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .description("Mantenimiento programada")
                .stateVisit(StateVisit.PROPOSAL)
                .installationsServices(testInstallation)
                .technical(testTechnical)
                .build();

        testVisitDTO = VisitDTO.builder()
                .id(1L)
                .visitDate(LocalDateTime.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .description("Mantenimiento programada")
                .stateVisit(StateVisit.PROPOSAL)
                .installationsServicesId(1L)
                .technicalId(1L)
                .build();
    }

    @Test
    void findVisitByInstallationId_ShouldReturnVisitsList_WhenInstallationExists() {
        // Given
        when(visitRepository.findVisitByInstallationsServicesId(1L))
                .thenReturn(List.of(testVisit));

        // When
        List<VisitDTO> result = visitService.findVisitByInstallationId(1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Mantenimiento programada", result.get(0).getDescription());
        verify(visitRepository, times(1)).findVisitByInstallationsServicesId(1L);
    }

    @Test
    void findVisitByInstallationId_ShouldReturnEmptyList_WhenNoVisitsExist() {
        // Given
        when(visitRepository.findVisitByInstallationsServicesId(999L))
                .thenReturn(List.of());

        // When
        List<VisitDTO> result = visitService.findVisitByInstallationId(999L);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(visitRepository, times(1)).findVisitByInstallationsServicesId(999L);
    }

    @Test
    void findByVisitId_ShouldReturnVisitDTO_WhenVisitExists() {
        // Given
        when(visitRepository.findById(1L))
                .thenReturn(Optional.of(testVisit));

        // When
        VisitDTO result = visitService.findByVisitId(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Mantenimiento programada", result.getDescription());
        verify(visitRepository, times(1)).findById(1L);
    }

    @Test
    void saveVisit_ShouldReturnSavedVisitDTO_WhenValidData() {
        // Given
        when(installationsServicesRepository.findById(1L))
                .thenReturn(Optional.of(testInstallation));
        when(technicalRepository.findById(1L))
                .thenReturn(Optional.of(testTechnical));
        when(visitRepository.save(any(Visits.class)))
                .thenReturn(testVisit);

        // When
        VisitDTO result = visitService.saveVisit(testVisitDTO);

        // Then
        assertNotNull(result);
        verify(visitRepository, times(1)).save(any(Visits.class));
        verify(installationsServicesRepository, times(1)).findById(1L);
        verify(technicalRepository, times(1)).findById(1L);
    }

    @Test
    void updateVisit_ShouldReturnUpdatedVisitDTO_WhenVisitExists() {
        // Given
        VisitDTO updateDTO = VisitDTO.builder()
                .id(1L)
                .visitDate(LocalDateTime.now())
                .startTime(LocalTime.of(11, 0))
                .endTime(LocalTime.of(12, 0))
                .description("Visita actualizada")
                .stateVisit(StateVisit.EXECUTED)
                .installationsServicesId(1L)
                .technicalId(1L)
                .build();

        when(visitRepository.findById(1L))
                .thenReturn(Optional.of(testVisit));

        // When
        VisitDTO result = visitService.updateVisit(updateDTO);

        // Then
        assertNotNull(result);
        verify(visitRepository, times(1)).findById(1L);
    }

    @Test
    void deleteVisit_ShouldCallDeleteRepository_WhenVisitExists() {
        // Given
        when(visitRepository.existsById(1L))
                .thenReturn(true);

        // When
        visitService.deleteVisit(1L);

        // Then
        verify(visitRepository, times(1)).existsById(1L);
        verify(visitRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteVisit_ShouldThrowException_WhenVisitNotExists() {
        // Given
        when(visitRepository.existsById(999L))
                .thenReturn(false);

        // When & Then
        assertThrows(ResourseNotFoundException.class, () -> {
            visitService.deleteVisit(999L);
        });

        verify(visitRepository, times(1)).existsById(999L);
        verify(visitRepository, never()).deleteById(anyLong());
    }
}