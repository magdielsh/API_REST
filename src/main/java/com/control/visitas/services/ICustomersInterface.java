package com.control.visitas.services;
import com.control.visitas.dtos.CustomersDTO;
import com.control.visitas.dtos.CustomersRequestDTO;
import com.control.visitas.dtos.CustomersResponseDTO;

public interface ICustomersInterface {

    CustomersResponseDTO findAllCustomers(int pageNumber, int pageSize);

    CustomersResponseDTO findFilterCustomers(String search, int pageNumber, int pageSize);

    CustomersDTO findCustomerByCode (String customerCode);

    CustomersResponseDTO findCustomersByTechnical (Long technicalId);

    CustomersDTO saveCustomer (CustomersRequestDTO customersRequestDTO);

    CustomersDTO updateCustomer(CustomersRequestDTO customersRequestDTO);

    void deleteCustomer (Long customerId);
}
