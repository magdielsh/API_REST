package com.control.visitas.util;

import com.control.visitas.dtos.customer.CustomersDTO;
import com.control.visitas.dtos.machine.MachineDTO;
import com.control.visitas.dtos.technical.TechnicalDTO;
import com.control.visitas.dtos.type_machine.TypeMachineDTO;
import com.control.visitas.models.entities.Customers;
import com.control.visitas.models.entities.Machine;
import com.control.visitas.models.entities.Technical;
import com.control.visitas.models.entities.Type_Machine;

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
                    new TechnicalDTO(customers.getTechnical().getId(),
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

    /**
     * Mapper Technical to TechnicalDTO
     */
    public static TechnicalDTO technicalToDTO(Technical technical){

        if (technical == null) return null;

        return new TechnicalDTO(
                technical.getId(),
                technical.getName(),
                technical.getEmail(),
                technical.getMobilePhone(),
                technical.getAddress(),
                technical.getProvince(),
                technical.getLocality(),
                technical.getCoordinates()
                );
    }

    /**
     * Mapper TypeMachine to TypeMachineDTO
     */

    public static TypeMachineDTO typeMachineToDTO(Type_Machine typeMachine){

        if (typeMachine == null) return null;

        return new TypeMachineDTO(
                typeMachine.getId(),
                typeMachine.getTypeMachine()
        );
    }

    /**
     * Mapper Machine to MachineDTO
     */

    public static MachineDTO machineToDTO (Machine machine){

        if (machine == null) return null;

        return new MachineDTO(
                machine.getId(),
                machine.getIdentifier(),
                machine.getSerialNumber(),
                machine.getBrand(),
                machine.getModel(),
                machine.getCustomer().getId(),
                machine.getTypeMachine().getId()
        );

    }
}
