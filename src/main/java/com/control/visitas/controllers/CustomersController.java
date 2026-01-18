package com.control.visitas.controllers;

import com.control.visitas.dtos.*;
import com.control.visitas.services.CustomersServices;
import com.control.visitas.services.TechnicalService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/customer")
@AllArgsConstructor
public class CustomersController {

    private final CustomersServices customersServices;

    private final TechnicalService technicalService;

    @GetMapping("/findAll")
    public ResponseEntity<CustomersResponseDTO> findAll(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "0") int pageSize
    ){

        CustomersResponseDTO customersResponseDTO = customersServices.findAllCustomers(pageNumber, pageSize);

        return ResponseEntity.ok(customersResponseDTO);
    }

    @GetMapping("/findFilterCustomers")
    public ResponseEntity<CustomersResponseDTO> findFilterCustomers(
            @RequestParam(name = "search") String search,
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "50") int pageSize
    ){
        CustomersResponseDTO customersResponseDTO = customersServices.findFilterCustomers(search, pageNumber, pageSize);

        return ResponseEntity.ok(customersResponseDTO);
    }

    @GetMapping("find/{customerCode}")
    public ResponseEntity<CustomersDTO> findCustomerByCode(@PathVariable String customerCode){

        CustomersDTO customersDTO = customersServices.findCustomerByCode(customerCode);

        return ResponseEntity.ok(customersDTO);
    }

    @GetMapping("findByTechnical/{technicalId}")
    public ResponseEntity<CustomersResponseDTO> findCustomersByTechnical(@PathVariable Long technicalId){

        CustomersResponseDTO customersResponseDTO = customersServices.findCustomersByTechnical(technicalId);

        return ResponseEntity.ok(customersResponseDTO);
    }

    @PostMapping("/saveCustomer")
    public ResponseEntity<CustomersDTO> saveCustomer(@RequestBody CustomersRequestDTO customersRequestDTO){

        if(customersRequestDTO.getCustomerCode().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        CustomersDTO customersDTO = customersServices.saveCustomer(customersRequestDTO);

        return ResponseEntity.ok(customersDTO);
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<CustomersDTO> updateCustomer(@RequestBody CustomersRequestDTO customersRequestDTO){

        if(customersRequestDTO.getCustomerCode().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(customersServices.updateCustomer(customersRequestDTO));
    }

    @DeleteMapping("deleteCustomer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId){

        if(customerId != null){
            customersServices.deleteCustomer(customerId);
            return ResponseEntity.ok("Registro Eliminado");
        }

        return ResponseEntity.badRequest().build();
    }

}
