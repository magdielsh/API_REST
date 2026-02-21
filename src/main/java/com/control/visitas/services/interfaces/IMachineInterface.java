package com.control.visitas.services.interfaces;

import com.control.visitas.dtos.machine.MachineDTO;
import com.control.visitas.dtos.machine.MachineResponseDTO;

public interface IMachineInterface {

    MachineResponseDTO getAllMachineByCustomer(Long customerId, int pageNumber, int pageSize);

    MachineDTO findMachineById (Long machineId);

    MachineDTO saveMachine(MachineDTO machineDTO);

    MachineDTO updateMachine(MachineDTO machineDTO);

    void deleteMachine (Long machineId);
}
