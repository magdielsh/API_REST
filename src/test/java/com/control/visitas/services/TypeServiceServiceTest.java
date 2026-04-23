package com.control.visitas.services;

import com.control.visitas.dtos.TypeServiceDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.Type_Services;
import com.control.visitas.repository.TypeServicesRepository;
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
class TypeServiceServiceTest {

    @Mock
    private TypeServicesRepository typeServicesRepository;

    @InjectMocks
    private TypeServiceService typeServiceService;

    private Type_Services testTypeService;

    @BeforeEach
    void setUp() {
        testTypeService = Type_Services.builder()
                .id(1L)
                .typeService("Mantenimiento")
                .build();
    }

    @Test
    void findAllTypeServices_ShouldReturnTypeServiceList() {
        // Given
        when(typeServicesRepository.findAll())
                .thenReturn(List.of(testTypeService));

        // When
        List<TypeServiceDTO> result = typeServiceService.findAllTypeServices();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Mantenimiento", result.get(0).getTypeService());
        verify(typeServicesRepository, times(1)).findAll();
    }

    @Test
    void findAllTypeServices_ShouldReturnEmptyList_WhenNoTypeServices() {
        // Given
        when(typeServicesRepository.findAll())
                .thenReturn(List.of());

        // When
        List<TypeServiceDTO> result = typeServiceService.findAllTypeServices();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(typeServicesRepository, times(1)).findAll();
    }

    @Test
    void saveTypeService_ShouldReturnSavedTypeServiceDTO() {
        // Given
        TypeServiceDTO newDTO = TypeServiceDTO.builder()
                .typeService("Instalación")
                .build();

        when(typeServicesRepository.save(any(Type_Services.class)))
                .thenReturn(testTypeService);

        // When
        TypeServiceDTO result = typeServiceService.saveTypeService(newDTO);

        // Then
        assertNotNull(result);
        verify(typeServicesRepository, times(1)).save(any(Type_Services.class));
    }

    @Test
    void updateTypeService_ShouldReturnUpdatedTypeServiceDTO() {
        // Given
        TypeServiceDTO updateDTO = TypeServiceDTO.builder()
                .id(1L)
                .typeService("Mantenimiento Actualizado")
                .build();

        when(typeServicesRepository.getReferenceById(1L))
                .thenReturn(testTypeService);
        when(typeServicesRepository.save(any(Type_Services.class)))
                .thenReturn(testTypeService);

        // When
        TypeServiceDTO result = typeServiceService.updateTypeService(updateDTO);

        // Then
        assertNotNull(result);
        verify(typeServicesRepository, times(1)).getReferenceById(1L);
        verify(typeServicesRepository, times(1)).save(any(Type_Services.class));
    }

    @Test
    void deleteTypeService_ShouldCallDeleteRepository_WhenExists() {
        // Given
        when(typeServicesRepository.existsById(1L))
                .thenReturn(true);

        // When
        typeServiceService.deleteTypeService(1L);

        // Then
        verify(typeServicesRepository, times(1)).existsById(1L);
        verify(typeServicesRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTypeService_ShouldThrowException_WhenNotExists() {
        // Given
        when(typeServicesRepository.existsById(999L))
                .thenReturn(false);

        // When & Then
        assertThrows(ResourseNotFoundException.class, () -> {
            typeServiceService.deleteTypeService(999L);
        });

        verify(typeServicesRepository, times(1)).existsById(999L);
        verify(typeServicesRepository, never()).deleteById(anyLong());
    }
}