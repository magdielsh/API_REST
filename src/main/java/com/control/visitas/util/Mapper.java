package com.control.visitas.util;

import com.control.visitas.dtos.CustomersDTO;
import com.control.visitas.dtos.TechnicalResponse;
import com.control.visitas.models.entities.Customers;

public class Mapper {

    /**
     * Mapper Customer to CustomerDTO
     */
    public static CustomersDTO customerToDTO(Customers customers){

        if (customers == null) return null;

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
