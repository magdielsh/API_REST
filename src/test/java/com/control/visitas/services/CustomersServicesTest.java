package com.control.visitas.services;

import com.control.visitas.dtos.customer.CustomersDTO;
import com.control.visitas.dtos.customer.CustomersRequestDTO;
import com.control.visitas.dtos.customer.CustomersResponseDTO;
import com.control.visitas.dtos.technical.TechnicalRequestDTO;
import com.control.visitas.exceptions.InvalidParameterException;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.Customers;
import com.control.visitas.models.entities.Technical;
import com.control.visitas.repository.CustomersRepository;
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
class CustomersServicesTest {

    @Mock
    private CustomersRepository customersRepository;

    @Mock
    private TechnicalService technicalService;

    @InjectMocks
    private CustomersServices customersServices;

    private Customers testCustomer;
    private CustomersRequestDTO testCustomerRequestDTO;
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

        testCustomer = Customers.builder()
                .id(1L)
                .customerCode("C00001")
                .isEnabled(true)
                .name("Empresa Test")
                .province("Buenos Aires")
                .coordinates("-34.60,-58.38")
                .email("test@empresa.com")
                .landlinePhone("1234567")
                .mobilePhone("987654321")
                .address("Calle 123")
                .description("Empresa de prueba")
                .zipCode(1000L)
                .workSchedule("9:00-18:00")
                .locality("Capital")
                .country("Argentina")
                .webPage("www.test.com")
                .technical(testTechnical)
                .build();

        TechnicalRequestDTO technicalRequestDTO = TechnicalRequestDTO.builder()
                .id(1L)
                .name("Juan Perez")
                .email("juan@test.com")
                .mobilePhone("123456789")
                .address("Calle 123")
                .province("Buenos Aires")
                .locality("Capital")
                .coordinates("-34.60,-58.38")
                .build();

        testCustomerRequestDTO = CustomersRequestDTO.builder()
                .id(1L)
                .customerCode("C00001")
                .isEnabled(true)
                .name("Empresa Test")
                .province("Buenos Aires")
                .coordinates("-34.60,-58.38")
                .email("test@empresa.com")
                .landlinePhone("1234567")
                .mobilePhone("987654321")
                .address("Calle 123")
                .description("Empresa de prueba")
                .zipCode(1000L)
                .workSchedule("9:00-18:00")
                .locality("Capital")
                .country("Argentina")
                .webPage("www.test.com")
                .technical(technicalRequestDTO)
                .build();
    }

    @Test
    void findAllCustomers_ShouldReturnCustomersResponseDTO() {
        // Given
        when(customersRepository.findAll())
                .thenReturn(List.of(testCustomer));

        // When
        CustomersResponseDTO result = customersServices.findAllCustomers(0, 10);

        // Then
        assertNotNull(result);
        assertNotNull(result.getCustomers());
        assertEquals(1, result.getCustomers().size());
        verify(customersRepository, times(1)).findAll();
    }

    @Test
    void findAllCustomers_ShouldReturnEmptyList_WhenNoCustomers() {
        // Given
        when(customersRepository.findAll())
                .thenReturn(List.of());

        // When
        CustomersResponseDTO result = customersServices.findAllCustomers(0, 10);

        // Then
        assertNotNull(result);
        assertTrue(result.getCustomers().isEmpty());
        verify(customersRepository, times(1)).findAll();
    }

    @Test
    void findFilterCustomers_ShouldReturnFilteredCustomers() {
        // Given
        when(customersRepository.filterCustomers("test"))
                .thenReturn(List.of(testCustomer));

        // When
        CustomersResponseDTO result = customersServices.findFilterCustomers("test", 1, 10);

        // Then
        assertNotNull(result);
        assertNotNull(result.getCustomers());
        verify(customersRepository, times(1)).filterCustomers("test");
    }

    @Test
    void findCustomerByCode_ShouldReturnCustomerDTO_WhenExists() {
        // Given
        when(customersRepository.findCustomersByCustomerCode("C00001"))
                .thenReturn(testCustomer);
        when(customersRepository.existsByCustomerCode("C00001"))
                .thenReturn(true);

        // When
        CustomersDTO result = customersServices.findCustomerByCode("C00001");

        // Then
        assertNotNull(result);
        assertEquals("C00001", result.customerCode());
        verify(customersRepository, times(1)).findCustomersByCustomerCode("C00001");
    }

    @Test
    void findCustomerByCode_ShouldThrowException_WhenInvalidCode() {
        // When & Then
        assertThrows(InvalidParameterException.class, () -> {
            customersServices.findCustomerByCode("INVALID");
        });
    }

    @Test
    void findCustomerByCode_ShouldThrowException_WhenNotExists() {
        // Given
        when(customersRepository.existsByCustomerCode("X99999"))
                .thenReturn(false);

        // When & Then
        assertThrows(ResourseNotFoundException.class, () -> {
            customersServices.findCustomerByCode("X99999");
        });
    }

    @Test
    void findCustomersByTechnical_ShouldReturnCustomersList() {
        // Given
        when(customersRepository.findAllByTechnicalId(1L))
                .thenReturn(List.of(testCustomer));

        // When
        CustomersResponseDTO result = customersServices.findCustomersByTechnical(1L);

        // Then
        assertNotNull(result);
        assertNotNull(result.getCustomers());
        verify(customersRepository, times(1)).findAllByTechnicalId(1L);
    }

    @Test
    void saveCustomer_ShouldReturnSavedCustomerDTO_WhenValidData() {
        // Given
        CustomersRequestDTO newDTO = CustomersRequestDTO.builder()
                .customerCode("C00002")
                .isEnabled(true)
                .name("Nueva Empresa")
                .province("Buenos Aires")
                .coordinates("-34.60,-58.38")
                .email("nueva@empresa.com")
                .landlinePhone("1234567")
                .mobilePhone("987654321")
                .address("Calle 456")
                .description("Nueva empresa")
                .zipCode(2000L)
                .workSchedule("9:00-18:00")
                .locality("Capital")
                .country("Argentina")
                .webPage("www.nueva.com")
                .build();

        when(customersRepository.save(any(Customers.class)))
                .thenReturn(testCustomer);

        // When
        CustomersDTO result = customersServices.saveCustomer(newDTO);

        // Then
        assertNotNull(result);
        verify(customersRepository, times(1)).save(any(Customers.class));
    }

    @Test
    void saveCustomer_ShouldSaveWithTechnical_WhenTechnicalProvided() {
        // Given
        TechnicalRequestDTO techRequest = TechnicalRequestDTO.builder()
                .id(1L)
                .name("Juan Perez")
                .email("juan@test.com")
                .mobilePhone("123456789")
                .address("Calle 123")
                .province("Buenos Aires")
                .locality("Capital")
                .coordinates("-34.60,-58.38")
                .build();

        CustomersRequestDTO newDTO = CustomersRequestDTO.builder()
                .customerCode("C00002")
                .isEnabled(true)
                .name("Nueva Empresa")
                .province("Buenos Aires")
                .coordinates("-34.60,-58.38")
                .email("nueva@empresa.com")
                .landlinePhone("1234567")
                .mobilePhone("987654321")
                .address("Calle 456")
                .description("Nueva empresa")
                .zipCode(2000L)
                .workSchedule("9:00-18:00")
                .locality("Capital")
                .country("Argentina")
                .webPage("www.nueva.com")
                .technical(techRequest)
                .build();

        when(customersRepository.save(any(Customers.class)))
                .thenReturn(testCustomer);
        when(technicalService.technicalById(1L))
                .thenReturn(testTechnical);

        // When
        CustomersDTO result = customersServices.saveCustomer(newDTO);

        // Then
        assertNotNull(result);
        verify(customersRepository, times(1)).save(any(Customers.class));
    }

    @Test
    void updateCustomer_ShouldReturnUpdatedCustomerDTO() {
        // Given
        when(customersRepository.findCustomersByCustomerCode("C00001"))
                .thenReturn(testCustomer);
        when(customersRepository.existsByCustomerCode("C00001"))
                .thenReturn(true);
        when(customersRepository.save(any(Customers.class)))
                .thenReturn(testCustomer);

        // When
        CustomersDTO result = customersServices.updateCustomer(testCustomerRequestDTO);

        // Then
        assertNotNull(result);
        verify(customersRepository, times(1)).findCustomersByCustomerCode("C00001");
        verify(customersRepository, times(1)).save(any(Customers.class));
    }

    @Test
    void deleteCustomer_ShouldCallDeleteRepository_WhenExists() {
        // Given
        when(customersRepository.existsByCustomerCode("C00001"))
                .thenReturn(true);

        // When
        customersServices.deleteCustomer("C00001");

        // Then
        verify(customersRepository, times(1)).existsByCustomerCode("C00001");
        verify(customersRepository, times(1)).deleteByCustomerCode("C00001");
    }

    @Test
    void deleteCustomer_ShouldThrowException_WhenInvalidCode() {
        // When & Then
        assertThrows(InvalidParameterException.class, () -> {
            customersServices.deleteCustomer("INVALID");
        });
    }

    @Test
    void deleteCustomer_ShouldThrowException_WhenNotExists() {
        // Given
        when(customersRepository.existsByCustomerCode("X99999"))
                .thenReturn(false);

        // When & Then
        assertThrows(ResourseNotFoundException.class, () -> {
            customersServices.deleteCustomer("X99999");
        });
    }
}