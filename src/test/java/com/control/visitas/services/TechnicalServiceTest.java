package com.control.visitas.services;

import com.control.visitas.dtos.PagingDataDTO;
import com.control.visitas.dtos.technical.TechnicalDTO;
import com.control.visitas.dtos.technical.TechnicalRequestDTO;
import com.control.visitas.dtos.technical.TechnicalResponseDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.Technical;
import com.control.visitas.repository.TechnicalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TechnicalServiceTest {

    @Mock
    private TechnicalRepository technicalRepository;

    @InjectMocks
    private TechnicalService technicalService;

    private Technical testTechnical;
    private TechnicalRequestDTO testTechnicalRequestDTO;

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
    }

    @Test
    void findAllTechnical_ShouldReturnTechnicalResponseDTO() {
        // Given
        when(technicalRepository.findAll())
                .thenReturn(List.of(testTechnical));

        // When
        TechnicalResponseDTO result = technicalService.findAllTechnical(1, 10);

        // Then
        assertNotNull(result);
        assertNotNull(result.getTechnical());
        assertEquals(1, result.getTechnical().size());
        verify(technicalRepository, times(1)).findAll();
    }

    @Test
    void findAllTechnical_ShouldReturnEmptyList_WhenNoTechnicals() {
        // Given
        when(technicalRepository.findAll())
                .thenReturn(List.of());

        // When
        TechnicalResponseDTO result = technicalService.findAllTechnical(1, 10);

        // Then
        assertNotNull(result);
        assertTrue(result.getTechnical().isEmpty());
        verify(technicalRepository, times(1)).findAll();
    }

    @Test
    void findFilterTechnical_ShouldReturnFilteredTechnicals() {
        // Given
        when(technicalRepository.filterTechnical("Juan"))
                .thenReturn(List.of(testTechnical));

        // When
        TechnicalResponseDTO result = technicalService.findFilterTechnical("Juan", 1, 10);

        // Then
        assertNotNull(result);
        assertNotNull(result.getTechnical());
        verify(technicalRepository, times(1)).filterTechnical("Juan");
    }

    @Test
    void findTechnicalById_ShouldReturnTechnicalDTO() {
        // Given
        when(technicalRepository.getReferenceById(1L))
                .thenReturn(testTechnical);

        // When
        TechnicalDTO result = technicalService.findTechnicalById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Juan Perez", result.name());
        verify(technicalRepository, times(1)).getReferenceById(1L);
    }

    @Test
    void technicalById_ShouldReturnTechnicalEntity_WhenExists() {
        // Given
        when(technicalRepository.existsById(1L))
                .thenReturn(true);
        when(technicalRepository.getReferenceById(1L))
                .thenReturn(testTechnical);

        // When
        Technical result = technicalService.technicalById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Juan Perez", result.getName());
        verify(technicalRepository, times(1)).existsById(1L);
        verify(technicalRepository, times(1)).getReferenceById(1L);
    }

    @Test
    void technicalById_ShouldThrowException_WhenNotExists() {
        // Given
        when(technicalRepository.existsById(999L))
                .thenReturn(false);

        // When & Then
        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> {
            technicalService.technicalById(999L);
        });

        verify(technicalRepository, times(1)).existsById(999L);
    }

    @Test
    void saveTechnical_ShouldReturnSavedTechnicalDTO() {
        // Given
        TechnicalRequestDTO newDTO = TechnicalRequestDTO.builder()
                .name("Pedro Gomez")
                .email("pedro@test.com")
                .mobilePhone("987654321")
                .address("Avenida 456")
                .province("Cordoba")
                .locality("Capital")
                .coordinates("-31.42,-64.18")
                .build();

        when(technicalRepository.save(any(Technical.class)))
                .thenReturn(testTechnical);

        // When
        TechnicalDTO result = technicalService.saveTechnical(newDTO);

        // Then
        assertNotNull(result);
        verify(technicalRepository, times(1)).save(any(Technical.class));
    }

    @Test
    void updateTechnical_ShouldReturnUpdatedTechnicalDTO() {
        // Given
        TechnicalRequestDTO updateDTO = TechnicalRequestDTO.builder()
                .id(1L)
                .name("Juan Actualizado")
                .email("juan.nuevo@test.com")
                .mobilePhone("111111111")
                .address("Nueva Calle 789")
                .province("Santa Fe")
                .locality("Rosario")
                .coordinates("-32.94,-60.64")
                .build();

        when(technicalRepository.getReferenceById(1L))
                .thenReturn(testTechnical);
        when(technicalRepository.save(any(Technical.class)))
                .thenReturn(testTechnical);

        // When
        TechnicalDTO result = technicalService.updateTechnical(updateDTO);

        // Then
        assertNotNull(result);
        verify(technicalRepository, times(1)).getReferenceById(1L);
        verify(technicalRepository, times(1)).save(any(Technical.class));
    }

    @Test
    void deleteTechnical_ShouldCallDeleteRepository_WhenExists() {
        // Given
        when(technicalRepository.existsById(1L))
                .thenReturn(true);

        // When
        technicalService.deleteTechnical(1L);

        // Then
        verify(technicalRepository, times(1)).existsById(1L);
        verify(technicalRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTechnical_ShouldThrowException_WhenNotExists() {
        // Given
        when(technicalRepository.existsById(999L))
                .thenReturn(false);

        // When & Then
        assertThrows(ResourseNotFoundException.class, () -> {
            technicalService.deleteTechnical(999L);
        });

        verify(technicalRepository, times(1)).existsById(999L);
        verify(technicalRepository, never()).deleteById(anyLong());
    }
}