package com.control.visitas.services;

import com.control.visitas.dtos.TypeMachineDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.Type_Machine;
import com.control.visitas.repository.TypeMachineRepository;
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
class TypeMachineServiceTest {

    @Mock
    private TypeMachineRepository typeMachineRepository;

    @InjectMocks
    private TypeMachineService typeMachineService;

    private Type_Machine testTypeMachine;

    @BeforeEach
    void setUp() {
        testTypeMachine = Type_Machine.builder()
                .id(1L)
                .typeMachine("Servidor")
                .build();
    }

    @Test
    void getAllTypeMachine_ShouldReturnTypeMachineList() {
        // Given
        when(typeMachineRepository.findAll())
                .thenReturn(List.of(testTypeMachine));

        // When
        List<TypeMachineDTO> result = typeMachineService.getAllTypeMachine();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Servidor", result.get(0).getTypeMachine());
        verify(typeMachineRepository, times(1)).findAll();
    }

    @Test
    void getAllTypeMachine_ShouldReturnEmptyList_WhenNoTypeMachines() {
        // Given
        when(typeMachineRepository.findAll())
                .thenReturn(List.of());

        // When
        List<TypeMachineDTO> result = typeMachineService.getAllTypeMachine();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(typeMachineRepository, times(1)).findAll();
    }

    @Test
    void saveTypeMachine_ShouldReturnSavedTypeMachineDTO() {
        // Given
        TypeMachineDTO newDTO = TypeMachineDTO.builder()
                .typeMachine("Workstation")
                .build();

        when(typeMachineRepository.save(any(Type_Machine.class)))
                .thenReturn(testTypeMachine);

        // When
        TypeMachineDTO result = typeMachineService.saveTypeMachine(newDTO);

        // Then
        assertNotNull(result);
        verify(typeMachineRepository, times(1)).save(any(Type_Machine.class));
    }

    @Test
    void updateTypeMachine_ShouldReturnUpdatedTypeMachineDTO() {
        // Given
        TypeMachineDTO updateDTO = TypeMachineDTO.builder()
                .id(1L)
                .typeMachine("Servidor Actualizado")
                .build();

        when(typeMachineRepository.getReferenceById(1L))
                .thenReturn(testTypeMachine);
        when(typeMachineRepository.save(any(Type_Machine.class)))
                .thenReturn(testTypeMachine);

        // When
        TypeMachineDTO result = typeMachineService.updateTypeMachine(updateDTO);

        // Then
        assertNotNull(result);
        verify(typeMachineRepository, times(1)).getReferenceById(1L);
        verify(typeMachineRepository, times(1)).save(any(Type_Machine.class));
    }

    @Test
    void deleteTypeMachine_ShouldCallDeleteRepository_WhenExists() {
        // Given
        when(typeMachineRepository.existsById(1L))
                .thenReturn(true);

        // When
        typeMachineService.deleteTypeMachine(1L);

        // Then
        verify(typeMachineRepository, times(1)).existsById(1L);
        verify(typeMachineRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTypeMachine_ShouldThrowException_WhenNotExists() {
        // Given
        when(typeMachineRepository.existsById(999L))
                .thenReturn(false);

        // When & Then
        assertThrows(ResourseNotFoundException.class, () -> {
            typeMachineService.deleteTypeMachine(999L);
        });

        verify(typeMachineRepository, times(1)).existsById(999L);
        verify(typeMachineRepository, never()).deleteById(anyLong());
    }
}