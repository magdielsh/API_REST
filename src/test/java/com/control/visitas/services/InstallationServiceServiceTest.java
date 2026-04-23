package com.control.visitas.services;

import com.control.visitas.dtos.PagingDataDTO;
import com.control.visitas.dtos.installation_service.InstallationServiceDTO;
import com.control.visitas.dtos.installation_service.InstallationServiceResponseDTO;
import com.control.visitas.dtos.machine.MachineDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.*;
import com.control.visitas.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstallationServiceServiceTest {

    @Mock
    private InstallationsServicesRepository installationsServicesRepository;

    @Mock
    private TypeServicesRepository typeServicesRepository;

    @Mock
    private CustomersRepository customersRepository;

    @Mock
    private TechnicalRepository technicalRepository;

    @Mock
    private MachineRepository machineRepository;

    @InjectMocks
    private InstallationServiceService installationServiceService;

    private InstallationsServices testInstallation;
    private InstallationServiceDTO testInstallationDTO;
    private Customers testCustomer;
    private Technical testTechnical;
    private Type_Services testTypeService;
    private List<Machine> testMachines;

    @BeforeEach
    void setUp() {
        testTechnical = Technical.builder()
                .id(1L)
                .name("Juan Perez")
                .email("juan@test.com")
                .build();

        testCustomer = Customers.builder()
                .id(1L)
                .customerCode("C00001")
                .name("Empresa Test")
                .build();

        testTypeService = Type_Services.builder()
                .id(1L)
                .typeService("Mantenimiento")
                .build();

        Machine machine = Machine.builder()
                .id(1L)
                .identifier(100)
                .serialNumber("SN123456")
                .brand("Dell")
                .model("PowerEdge")
                .customer(testCustomer)
                .typeMachine(Type_Machine.builder().id(1L).typeMachine("Servidor").build())
                .build();
        
        testMachines = new ArrayList<>();
        testMachines.add(machine);

        testInstallation = InstallationsServices.builder()
                .id(1L)
                .codeInstallation("INST001")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .quantityEquipments(5)
                .typeServices(testTypeService)
                .customers(testCustomer)
                .technical(testTechnical)
                .machines(testMachines)
                .build();

        MachineDTO machineDTO = MachineDTO.builder()
                .id(1L)
                .identifier(100)
                .serialNumber("SN123456")
                .brand("Dell")
                .model("PowerEdge")
                .customerId(1L)
                .typeMachineId(1L)
                .build();

        testInstallationDTO = InstallationServiceDTO.builder()
                .id(1L)
                .codeInstallation("INST001")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .quantityEquipments(5)
                .typeServicesId(1L)
                .customerId(1L)
                .technicalId(1L)
                .machines(List.of(machineDTO))
                .build();
    }

    @Test
    void findAllInstallationService_ShouldReturnInstallationServiceResponseDTO() {
        // Given
        when(installationsServicesRepository.findAll())
                .thenReturn(List.of(testInstallation));

        // When
        InstallationServiceResponseDTO result = installationServiceService.findAllInstallationService(1, 10);

        // Then
        assertNotNull(result);
        assertNotNull(result.getInstallationService());
        assertEquals(1, result.getInstallationService().size());
        verify(installationsServicesRepository, times(1)).findAll();
    }

    @Test
    void findAllInstallationService_ShouldReturnEmptyList_WhenNoInstallations() {
        // Given
        when(installationsServicesRepository.findAll())
                .thenReturn(List.of());

        // When
        InstallationServiceResponseDTO result = installationServiceService.findAllInstallationService(1, 10);

        // Then
        assertNotNull(result);
        assertTrue(result.getInstallationService().isEmpty());
        verify(installationsServicesRepository, times(1)).findAll();
    }

    @Test
    void findInstallationByCustomerId_ShouldReturnFilteredInstallations() {
        // Given
        when(installationsServicesRepository.filterInstallationService("test"))
                .thenReturn(List.of(testInstallation));

        // When
        InstallationServiceResponseDTO result = installationServiceService.findInstallationByCustomerId("test", 1, 10);

        // Then
        assertNotNull(result);
        assertNotNull(result.getInstallationService());
        verify(installationsServicesRepository, times(1)).filterInstallationService("test");
    }

    @Test
    void findInstallationServiceByCode_ShouldReturnInstallationServiceDTO() {
        // Given
        when(installationsServicesRepository.findInstallationsServicesByCodeInstallation("INST001"))
                .thenReturn(testInstallation);

        // When
        InstallationServiceDTO result = installationServiceService.findInstallationServiceByCode("INST001");

        // Then
        assertNotNull(result);
        assertEquals("INST001", result.getCodeInstallation());
        verify(installationsServicesRepository, times(1)).findInstallationsServicesByCodeInstallation("INST001");
    }

    @Test
    void saveInstallationService_ShouldReturnSavedInstallationServiceDTO() {
        // Given
        InstallationServiceDTO newDTO = InstallationServiceDTO.builder()
                .codeInstallation("INST002")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .quantityEquipments(3)
                .typeServicesId(1L)
                .customerId(1L)
                .technicalId(1L)
                .machines(List.of(MachineDTO.builder().id(1L).identifier(100).serialNumber("SN123456").brand("Dell").model("PowerEdge").customerId(1L).typeMachineId(1L).build()))
                .build();

        when(typeServicesRepository.getReferenceById(1L))
                .thenReturn(testTypeService);
        when(customersRepository.getReferenceById(1L))
                .thenReturn(testCustomer);
        when(technicalRepository.getReferenceById(1L))
                .thenReturn(testTechnical);
        when(machineRepository.findById(1L))
                .thenReturn(Optional.of(testMachines.get(0)));
        when(installationsServicesRepository.save(any(InstallationsServices.class)))
                .thenReturn(testInstallation);

        // When
        InstallationServiceDTO result = installationServiceService.saveInstallationService(newDTO);

        // Then
        assertNotNull(result);
        verify(installationsServicesRepository, times(1)).save(any(InstallationsServices.class));
    }

//    @Test
//    void updateInstallationService_ShouldThrowException_WhenNotExists() {
//        // Given
//        when(installationsServicesRepository.existsById(999L))
//                .thenReturn(false);
//
//        // When & Then
//        assertThrows(ResourseNotFoundException.class, () -> {
//            installationServiceService.updateInstallationService(testInstallationDTO);
//        });
//
//        verify(installationsServicesRepository, times(1)).existsById(999L);
//    }

    @Test
    void deleteInstallationService_ShouldCallDeleteRepository_WhenExists() {
        // Given
        when(installationsServicesRepository.existsById(1L))
                .thenReturn(true);

        // When
        installationServiceService.deleteInstallationService(1L);

        // Then
        verify(installationsServicesRepository, times(1)).existsById(1L);
        verify(installationsServicesRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteInstallationService_ShouldThrowException_WhenNotExists() {
        // Given
        when(installationsServicesRepository.existsById(999L))
                .thenReturn(false);

        // When & Then
        assertThrows(ResourseNotFoundException.class, () -> {
            installationServiceService.deleteInstallationService(999L);
        });

        verify(installationsServicesRepository, times(1)).existsById(999L);
        verify(installationsServicesRepository, never()).deleteById(anyLong());
    }
}