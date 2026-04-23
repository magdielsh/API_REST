package com.control.visitas.services;

import com.control.visitas.dtos.PagingDataDTO;
import com.control.visitas.dtos.machine.MachineDTO;
import com.control.visitas.dtos.machine.MachineResponseDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.Customers;
import com.control.visitas.models.entities.Machine;
import com.control.visitas.models.entities.Type_Machine;
import com.control.visitas.repository.CustomersRepository;
import com.control.visitas.repository.MachineRepository;
import com.control.visitas.repository.TypeMachineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MachineServiceTest {

    @Mock
    private MachineRepository machineRepository;

    @Mock
    private CustomersRepository customersRepository;

    @Mock
    private TypeMachineRepository typeMachineRepository;

    @InjectMocks
    private MachineService machineService;

    private Machine testMachine;
    private MachineDTO testMachineDTO;
    private Customers testCustomer;
    private Type_Machine testTypeMachine;

    @BeforeEach
    void setUp() {
        testCustomer = Customers.builder()
                .id(1L)
                .customerCode("C00001")
                .name("Empresa Test")
                .email("test@empresa.com")
                .province("Buenos Aires")
                .coordinates("-34.60,-58.38")
                .landlinePhone("1234567")
                .mobilePhone("987654321")
                .address("Calle 123")
                .zipCode(1000L)
                .workSchedule("9:00-18:00")
                .locality("Capital")
                .country("Argentina")
                .isEnabled(true)
                .build();

        testTypeMachine = Type_Machine.builder()
                .id(1L)
                .typeMachine("Servidor")
                .build();

        testMachine = Machine.builder()
                .id(1L)
                .identifier(100)
                .serialNumber("SN123456")
                .brand("Dell")
                .model("PowerEdge")
                .customer(testCustomer)
                .typeMachine(testTypeMachine)
                .build();

        testMachineDTO = MachineDTO.builder()
                .id(1L)
                .identifier(100)
                .serialNumber("SN123456")
                .brand("Dell")
                .model("PowerEdge")
                .customerId(1L)
                .typeMachineId(1L)
                .build();
    }

    @Test
    void getAllMachineByCustomer_ShouldReturnMachineResponseDTO_WhenCustomerExists() {
        // Given
        when(machineRepository.findAllMachineByCustomerId(1L))
                .thenReturn(List.of(testMachine));

        // When
        MachineResponseDTO result = machineService.getAllMachineByCustomer(1L, 1, 10);

        // Then
        assertNotNull(result);
        assertNotNull(result.getMachineDTOS());
        assertEquals(1, result.getMachineDTOS().size());
        assertEquals(100, result.getMachineDTOS().get(0).getIdentifier());
        verify(machineRepository, times(1)).findAllMachineByCustomerId(1L);
    }

    @Test
    void getAllMachineByCustomer_ShouldReturnEmptyList_WhenNoMachines() {
        // Given
        when(machineRepository.findAllMachineByCustomerId(999L))
                .thenReturn(List.of());

        // When
        MachineResponseDTO result = machineService.getAllMachineByCustomer(999L, 1, 10);

        // Then
        assertNotNull(result);
        assertTrue(result.getMachineDTOS().isEmpty());
        verify(machineRepository, times(1)).findAllMachineByCustomerId(999L);
    }

    @Test
    void findMachineById_ShouldReturnMachineDTO_WhenMachineExists() {
        // Given
        when(machineRepository.getReferenceById(1L))
                .thenReturn(testMachine);

        // When
        MachineDTO result = machineService.findMachineById(1L);

        // Then
        assertNotNull(result);
        assertEquals(100, result.getIdentifier());
        assertEquals("SN123456", result.getSerialNumber());
        verify(machineRepository, times(1)).getReferenceById(1L);
    }

    @Test
    void saveMachine_ShouldReturnSavedMachineDTO_WhenValidData() {
        // Given
        MachineDTO newMachineDTO = MachineDTO.builder()
                .identifier(200)
                .serialNumber("SN654321")
                .brand("HP")
                .model("ProLiant")
                .customerId(1L)
                .typeMachineId(1L)
                .build();

        when(customersRepository.getReferenceById(1L))
                .thenReturn(testCustomer);
        when(typeMachineRepository.getReferenceById(1L))
                .thenReturn(testTypeMachine);
        when(machineRepository.save(any(Machine.class)))
                .thenReturn(testMachine);

        // When
        MachineDTO result = machineService.saveMachine(newMachineDTO);

        // Then
        assertNotNull(result);
        verify(machineRepository, times(1)).save(any(Machine.class));
        verify(customersRepository, times(1)).getReferenceById(1L);
        verify(typeMachineRepository, times(1)).getReferenceById(1L);
    }

    @Test
    void updateMachine_ShouldReturnUpdatedMachineDTO_WhenMachineExists() {
        // Given
        MachineDTO updateDTO = MachineDTO.builder()
                .id(1L)
                .identifier(200)
                .serialNumber("SN999999")
                .brand("Lenovo")
                .model("ThinkSystem")
                .customerId(1L)
                .typeMachineId(1L)
                .build();

        when(machineRepository.getReferenceById(1L))
                .thenReturn(testMachine);
        when(customersRepository.getReferenceById(1L))
                .thenReturn(testCustomer);
        when(typeMachineRepository.getReferenceById(1L))
                .thenReturn(testTypeMachine);
        when(machineRepository.save(any(Machine.class)))
                .thenReturn(testMachine);

        // When
        MachineDTO result = machineService.updateMachine(updateDTO);

        // Then
        assertNotNull(result);
        verify(machineRepository, times(1)).getReferenceById(1L);
        verify(machineRepository, times(1)).save(any(Machine.class));
    }

    @Test
    void deleteMachine_ShouldCallDeleteRepository_WhenMachineExists() {
        // Given
        when(machineRepository.existsById(1L))
                .thenReturn(true);

        // When
        machineService.deleteMachine(1L);

        // Then
        verify(machineRepository, times(1)).existsById(1L);
        verify(machineRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteMachine_ShouldThrowException_WhenMachineNotExists() {
        // Given
        when(machineRepository.existsById(999L))
                .thenReturn(false);

        // When & Then
        assertThrows(ResourseNotFoundException.class, () -> {
            machineService.deleteMachine(999L);
        });

        verify(machineRepository, times(1)).existsById(999L);
        verify(machineRepository, never()).deleteById(anyLong());
    }
}