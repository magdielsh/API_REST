package com.control.visitas.services;

import com.control.visitas.dtos.customer.CustomersDTO;
import com.control.visitas.dtos.customer.CustomersRequestDTO;
import com.control.visitas.dtos.customer.CustomersResponseDTO;
import com.control.visitas.dtos.PagingDataDTO;
import com.control.visitas.exceptions.InvalidParameterException;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.Customers;
import com.control.visitas.repository.CustomersRepository;
import com.control.visitas.services.interfaces.ICustomersInterface;
import com.control.visitas.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomersServices implements ICustomersInterface {


    private final CustomersRepository customersRepository;

    private final TechnicalService technicalService;

    @Override
    public CustomersResponseDTO findAllCustomers(int pageNumber, int pageSize) {
        CustomersResponseDTO customersResponseDTO;
        PagingDataDTO pagingDataDTO;
        List<CustomersDTO> customersDTO;
        customersDTO = customersRepository.findAll()
                .stream()
                .map(Mapper::customerToDTO)
                .toList();
        pagingDataDTO = new PagingDataDTO(pageNumber, pageSize, customersDTO.size());
        customersResponseDTO = new CustomersResponseDTO(customersDTO, pagingDataDTO);

        return customersResponseDTO;
    }

    @Override
    public CustomersResponseDTO findFilterCustomers(String search, int pageNumber, int pageSize) {
        CustomersResponseDTO customersResponseDTO;
        PagingDataDTO pagingDataDTO;
        List<CustomersDTO> customersDTO;
        customersDTO = customersRepository.filterCustomers(search)
                .stream()
                .map(Mapper::customerToDTO)
                .toList();
        pagingDataDTO = new PagingDataDTO(pageNumber, pageSize, customersDTO.size());
        customersResponseDTO = new CustomersResponseDTO(customersDTO, pagingDataDTO);

        return customersResponseDTO;
    }

    @Override
    public CustomersDTO findCustomerByCode(String customerCode) {

        validateCustomerCode(customerCode);
        return Mapper.customerToDTO(customersRepository.findCustomersByCustomerCode(customerCode));

    }

    @Override
    public CustomersResponseDTO findCustomersByTechnical(Long technicalId) {

        CustomersResponseDTO customersResponseDTO;
        PagingDataDTO pagingDataDTO;
        List<CustomersDTO> customersDTO;
        customersDTO = customersRepository.findAllByTechnicalId(technicalId)
                .stream()
                .map(Mapper::customerToDTO)
                .toList();
        pagingDataDTO = new PagingDataDTO(1, 10, customersDTO.size());
        customersResponseDTO = new CustomersResponseDTO(customersDTO, pagingDataDTO);

        return customersResponseDTO;
    }

    @Override
    @Transactional
    public CustomersDTO saveCustomer(CustomersRequestDTO customersRequestDTO) {

        Customers customer;
        if (customersRequestDTO.getTechnical() == null){
            customer = customersRepository.save(Customers.builder()
                    .customerCode(customersRequestDTO.getCustomerCode())
                    .isEnabled(customersRequestDTO.getIsEnabled())
                    .name(customersRequestDTO.getName())
                    .province(customersRequestDTO.getProvince())
                    .coordinates(customersRequestDTO.getCoordinates())
                    .email(customersRequestDTO.getEmail())
                    .landlinePhone(customersRequestDTO.getLandlinePhone())
                    .mobilePhone(customersRequestDTO.getMobilePhone())
                    .address(customersRequestDTO.getAddress())
                    .description(customersRequestDTO.getDescription())
                    .zipCode(customersRequestDTO.getZipCode())
                    .workSchedule(customersRequestDTO.getWorkSchedule())
                    .locality(customersRequestDTO.getLocality())
                    .country(customersRequestDTO.getCountry())
                    .webPage(customersRequestDTO.getWebPage())
                    .build());
        }else{
            customer = customersRepository.save(Customers.builder()
                    .customerCode(customersRequestDTO.getCustomerCode())
                    .isEnabled(customersRequestDTO.getIsEnabled())
                    .name(customersRequestDTO.getName())
                    .province(customersRequestDTO.getProvince())
                    .coordinates(customersRequestDTO.getCoordinates())
                    .email(customersRequestDTO.getEmail())
                    .landlinePhone(customersRequestDTO.getLandlinePhone())
                    .mobilePhone(customersRequestDTO.getMobilePhone())
                    .address(customersRequestDTO.getAddress())
                    .description(customersRequestDTO.getDescription())
                    .zipCode(customersRequestDTO.getZipCode())
                    .workSchedule(customersRequestDTO.getWorkSchedule())
                    .locality(customersRequestDTO.getLocality())
                    .country(customersRequestDTO.getCountry())
                    .webPage(customersRequestDTO.getWebPage())
                    .technical(technicalService.technicalById(customersRequestDTO.getTechnical().getId()))
                    .build());
        }

        return Mapper.customerToDTO(customer);
    }

    @Override
    @Transactional
    public CustomersDTO updateCustomer(CustomersRequestDTO customersRequestDTO) {

        validateCustomerCode(customersRequestDTO.getCustomerCode());
        Customers customersUpdate = customersRepository
                .findCustomersByCustomerCode(customersRequestDTO.getCustomerCode());
        if (customersRequestDTO.getTechnical() == null){
            customersUpdate.setCustomerCode(customersRequestDTO.getCustomerCode());
            customersUpdate.setIsEnabled(customersRequestDTO.getIsEnabled());
            customersUpdate.setName(customersRequestDTO.getName());
            customersUpdate.setProvince(customersRequestDTO.getProvince());
            customersUpdate.setCoordinates(customersRequestDTO.getCoordinates());
            customersUpdate.setEmail(customersRequestDTO.getEmail());
            customersUpdate.setLandlinePhone(customersRequestDTO.getLandlinePhone());
            customersUpdate.setMobilePhone(customersRequestDTO.getMobilePhone());
            customersUpdate.setAddress(customersRequestDTO.getAddress());
            customersUpdate.setDescription(customersRequestDTO.getDescription());
            customersUpdate.setZipCode(customersRequestDTO.getZipCode());
            customersUpdate.setWorkSchedule(customersRequestDTO.getWorkSchedule());
            customersUpdate.setLocality(customersRequestDTO.getLocality());
            customersUpdate.setCountry(customersRequestDTO.getCountry());
            customersUpdate.setWebPage(customersRequestDTO.getWebPage());
            customersUpdate.setTechnical(null);
        }else{
            customersUpdate.setCustomerCode(customersRequestDTO.getCustomerCode());
            customersUpdate.setIsEnabled(customersRequestDTO.getIsEnabled());
            customersUpdate.setName(customersRequestDTO.getName());
            customersUpdate.setProvince(customersRequestDTO.getProvince());
            customersUpdate.setCoordinates(customersRequestDTO.getCoordinates());
            customersUpdate.setEmail(customersRequestDTO.getEmail());
            customersUpdate.setLandlinePhone(customersRequestDTO.getLandlinePhone());
            customersUpdate.setMobilePhone(customersRequestDTO.getMobilePhone());
            customersUpdate.setAddress(customersRequestDTO.getAddress());
            customersUpdate.setDescription(customersRequestDTO.getDescription());
            customersUpdate.setZipCode(customersRequestDTO.getZipCode());
            customersUpdate.setWorkSchedule(customersRequestDTO.getWorkSchedule());
            customersUpdate.setLocality(customersRequestDTO.getLocality());
            customersUpdate.setCountry(customersRequestDTO.getCountry());
            customersUpdate.setWebPage(customersRequestDTO.getWebPage());
            customersUpdate.setTechnical(technicalService.technicalById(customersRequestDTO.getTechnical().getId()));
        }

        return Mapper.customerToDTO(customersRepository.save(customersUpdate));
    }

    @Override
    @Transactional
    public void deleteCustomer(String customerCode) {

        validateCustomerCode(customerCode);
        customersRepository.deleteByCustomerCode(customerCode);
    }

    public void validateCustomerCode (String customerCode) {

        if (!customerCode.matches("^[A-Z]\\d{5}$")){
            throw new InvalidParameterException(customerCode);
        }
        if (!customersRepository.existsByCustomerCode(customerCode)) {
            throw new ResourseNotFoundException("Cliente con Código: " + customerCode + " no encontrado");
        }

    }

}
