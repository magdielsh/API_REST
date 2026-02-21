package com.control.visitas.services.interfaces;
import com.control.visitas.dtos.customer.CustomersDTO;
import com.control.visitas.dtos.customer.CustomersRequestDTO;
import com.control.visitas.dtos.customer.CustomersResponseDTO;

public interface ICustomersInterface {

    CustomersResponseDTO findAllCustomers(int pageNumber, int pageSize);

    CustomersResponseDTO findFilterCustomers(String search, int pageNumber, int pageSize);

    CustomersDTO findCustomerByCode (String customerCode);

    CustomersResponseDTO findCustomersByTechnical (Long technicalId);

    CustomersDTO saveCustomer (CustomersRequestDTO customersRequestDTO);

    CustomersDTO updateCustomer(CustomersRequestDTO customersRequestDTO);

    void deleteCustomer (String customerCode);

}
