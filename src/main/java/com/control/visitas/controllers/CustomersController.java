package com.control.visitas.controllers;

import com.control.visitas.dtos.*;
import com.control.visitas.models.entities.Customers;
import com.control.visitas.services.CustomersServices;
import com.control.visitas.services.TechnicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomersController {

    private final CustomersServices customersServices;

    private final TechnicalService technicalService;

    @Autowired
    public CustomersController(CustomersServices customersServices, TechnicalService technicalService) {
        this.customersServices = customersServices;
        this.technicalService = technicalService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<CustomersDTO>> findAll(){
        List<CustomersDTO> customersRespons = customersServices.getAllCustomers()
                .stream()
                .map(this::getCustomersResponse).toList();

        return ResponseEntity.ok(customersRespons);
    }

    @GetMapping("/findFilterCustomers")
    public ResponseEntity<CustomersResponseDTO> finFilterCustomers(
            @RequestParam(name = "search") String search,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "size", defaultValue = "50") int pageSize
    ){
        CustomersResponseDTO customersResponseDTO = new CustomersResponseDTO();
        PagingDataDTO pagingDataDTO;
        List<CustomersDTO> customersRespons;
        customersRespons = customersServices.getFiterCustomers(search,pageNumber,pageSize)
                .stream()
                .map(this::getCustomersResponse).toList();
       // if (customersRespons.size() > 0){
            pagingDataDTO = new PagingDataDTO(pageNumber, pageSize, customersRespons.size());
            customersResponseDTO = new CustomersResponseDTO(customersRespons, pagingDataDTO);
       // }else{
        //    customersRespons = customersServices.getAllCustomers()
        //            .stream()
        //            .map(this::getCustomersResponse).toList();
        //    pagingDataDTO = new PagingDataDTO(pageNumber, pageSize, customersRespons.size());
       //     customersResponseDTO = new CustomersResponseDTO(customersRespons, pagingDataDTO);
       // }

        return ResponseEntity.ok(customersResponseDTO);
    }

    @GetMapping("find/{customerCode}")
    public ResponseEntity<CustomersDTO> findCustomerByCode(@PathVariable String customerCode){
        CustomersDTO customersDTO = getCustomersResponse(customersServices.getCustomerByCode(customerCode));

        return ResponseEntity.ok(customersDTO);
    }

    @GetMapping("findByTechnical/{technicalId}")
    public ResponseEntity<List<CustomersDTO>> findCustomersByTechnical(@PathVariable Long technicalId){
        List<CustomersDTO> customersRespons = customersServices.getCustomersByTechnicalId(technicalId)
                .stream()
                .map(this::getCustomersResponse).toList();

        return ResponseEntity.ok(customersRespons);
    }

    @PostMapping("/saveCustomers")
    public ResponseEntity<String> saveCustomers(@RequestBody CustomersRequestDTO customersRequestDTO){
        if(customersRequestDTO.getCustomerCode().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        if (customersRequestDTO.getTechnical() == null){
            customersServices.saveCustomer(Customers.builder()
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
            customersServices.saveCustomer(Customers.builder()
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
                    .technical(technicalService.getTechnicalByID(customersRequestDTO.getTechnical().getId()))
                    .build());
        }


        return ResponseEntity.ok("Customer Created");
    }

    @DeleteMapping("deleteCustomers/{customerCode}")
    public ResponseEntity<String> deleteOrder(@PathVariable String customerCode){

        if(customerCode != null){
            customersServices.deleteCustomerByCode(customerCode);
            return ResponseEntity.ok("Registro Eliminado");
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/updateCustomers")
    public ResponseEntity<String> updateCustomers(@RequestBody CustomersRequestDTO customersRequestDTO){
        if(customersRequestDTO.getCustomerCode().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        Customers customersUpdate = customersServices.getCustomerByCode(customersRequestDTO.getCustomerCode());
        if (customersRequestDTO.getTechnical() == null){
           // System.out.print("El TECNICO ES NULO");
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
            customersUpdate.setTechnical(technicalService.getTechnicalByID(customersRequestDTO.getTechnical().getId()));
        }

        customersServices.updateCustomer(customersUpdate);

        return ResponseEntity.ok("Customer Updated");
    }

    private CustomersDTO getCustomersResponse(Customers customers){
        if (customers.getTechnical() == null){
            return new CustomersDTO(
                    customers.getId(),
                    customers.getCustomerCode(),
                    customers.getIsEnabled(),
                    customers.getName(),
                    customers.getProvince(),
                    customers.getCoordinates(),
                    customers.getEmail(),
                    customers.getLandlinePhone(),
                    customers.getMobilePhone(),
                    customers.getAddress(),
                    customers.getDescription(),
                    customers.getZipCode(),
                    customers.getWorkSchedule(),
                    customers.getLocality(),
                    customers.getCountry(),
                    customers.getWebPage(),
                    null
            );
        }else{
            return new CustomersDTO(
                    customers.getId(),
                    customers.getCustomerCode(),
                    customers.getIsEnabled(),
                    customers.getName(),
                    customers.getProvince(),
                    customers.getCoordinates(),
                    customers.getEmail(),
                    customers.getLandlinePhone(),
                    customers.getMobilePhone(),
                    customers.getAddress(),
                    customers.getDescription(),
                    customers.getZipCode(),
                    customers.getWorkSchedule(),
                    customers.getLocality(),
                    customers.getCountry(),
                    customers.getWebPage(),
                    new TechnicalResponse(customers.getTechnical().getId(),
                            customers.getTechnical().getName(),
                            customers.getTechnical().getEmail(),
                            customers.getTechnical().getMobilePhone(),
                            customers.getTechnical().getAddress(),
                            customers.getTechnical().getProvince(),
                            customers.getTechnical().getLocality(),
                            customers.getTechnical().getCoordinates())
            );
        }

    }
}
