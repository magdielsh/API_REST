package com.control.visitas.services;

import com.control.visitas.dtos.CustomersDTO;
import com.control.visitas.dtos.CustomersResponseDTO;
import com.control.visitas.dtos.PagingDataDTO;
import com.control.visitas.models.entities.Customers;
import com.control.visitas.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomersServices {


    private final CustomersRepository customersRepository;

    @Autowired
    public CustomersServices(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<Customers> getAllCustomers(){
        return customersRepository.findAll();
    }

    public List<Customers> getFiterCustomers(String search, int pageNumber, int pageSize){
        return customersRepository.filterCustomers(search);
    }

    public void saveCustomer(Customers customers){
        customersRepository.save(customers);
    }

    public Customers getCustomerByCode(String code){
        return customersRepository.findCustomersByCustomerCode(code);
    }

    public List<Customers> getCustomersByTechnicalId(Long technicalId){
        return customersRepository.findAllByTechnicalId(technicalId);
    }

    public void deleteCustomerByCode(String customersCode){
        customersRepository.deleteByCustomerCode(customersCode);
    }

    public void updateCustomer(Customers customers){
        customersRepository.save(customers);
    }

    public void deleteCustomerById(Long id){
        customersRepository.deleteById(id);
    }
}
