package com.control.visitas.controllers;

import com.control.visitas.dtos.PagingDataDTO;
import com.control.visitas.dtos.customer.CustomersDTO;
import com.control.visitas.dtos.customer.CustomersRequestDTO;
import com.control.visitas.dtos.customer.CustomersResponseDTO;
import com.control.visitas.services.CustomersServices;
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

@WebMvcTest(CustomersController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomersServices customersServices;

    @MockitoBean
    private TechnicalService technicalService;

    private CustomersDTO testCustomerDTO;
    private CustomersRequestDTO testCustomerRequestDTO;
    private CustomersResponseDTO testResponseDTO;

    @BeforeEach
    void setUp() {
        testCustomerDTO = new CustomersDTO(1L, "C00001", true, "Empresa Test", "Buenos Aires", 
                "-34.60,-58.38", "test@empresa.com", "1234567", "987654321", "Calle 123", 
                "Empresa de prueba", 1000L, "9:00-18:00", "Capital", "Argentina", "www.test.com", null);

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
                .build();

        PagingDataDTO pagingDataDTO = new PagingDataDTO(0, 10, 1);
        testResponseDTO = new CustomersResponseDTO(List.of(testCustomerDTO), pagingDataDTO);
    }

    @Test
    void findAll_ShouldReturnOk() throws Exception {
        // Given
        when(customersServices.findAllCustomers(anyInt(), anyInt()))
                .thenReturn(testResponseDTO);

        // When & Then
        mockMvc.perform(get("/api/customer/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers").isArray())
                .andExpect(jsonPath("$.customers[0].customerCode").value("C00001"));
    }

    @Test
    void findFilterCustomers_ShouldReturnOk() throws Exception {
        // Given
        when(customersServices.findFilterCustomers(anyString(), anyInt(), anyInt()))
                .thenReturn(testResponseDTO);

        // When & Then
        mockMvc.perform(get("/api/customer/findFilterCustomers")
                        .param("search", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers").isArray());
    }

    @Test
    void findCustomerByCode_ShouldReturnCustomerDTO() throws Exception {
        // Given
        when(customersServices.findCustomerByCode("C00001"))
                .thenReturn(testCustomerDTO);

        // When & Then
        mockMvc.perform(get("/api/customer/find/C00001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerCode").value("C00001"));
    }

    @Test
    void findCustomersByTechnical_ShouldReturnOk() throws Exception {
        // Given
        when(customersServices.findCustomersByTechnical(1L))
                .thenReturn(testResponseDTO);

        // When & Then
        mockMvc.perform(get("/api/customer/findByTechnical/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers").isArray());
    }

    @Test
    void saveCustomer_ShouldReturnCreated() throws Exception {
        // Given
        testCustomerRequestDTO = CustomersRequestDTO.builder()
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
                .build();

        when(customersServices.saveCustomer(any(CustomersRequestDTO.class)))
                .thenReturn(testCustomerDTO);

        // When & Then
        mockMvc.perform(post("/api/customer/saveCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerCode").value("C00001"));
    }

    @Test
    void updateCustomer_ShouldReturnOk() throws Exception {
        // Given
        when(customersServices.updateCustomer(any(CustomersRequestDTO.class)))
                .thenReturn(testCustomerDTO);

        // When & Then
        mockMvc.perform(put("/api/customer/updateCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerCode").value("C00001"));
    }

    @Test
    void deleteCustomer_ShouldReturnOk() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/customer/deleteCustomer/C00001"))
                .andExpect(status().isOk())
                .andExpect(content().string("Registro Eliminado"));
    }

    @Test
    void deleteCustomer_ShouldReturnError() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/customer/deleteCustomer/"))
                .andExpect(status().isNotFound());
    }
}