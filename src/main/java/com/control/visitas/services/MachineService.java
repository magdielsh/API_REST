package com.control.visitas.services;

import com.control.visitas.dtos.PagingDataDTO;
import com.control.visitas.dtos.machine.MachineDTO;
import com.control.visitas.dtos.machine.MachineResponseDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.Machine;
import com.control.visitas.repository.CustomersRepository;
import com.control.visitas.repository.MachineRepository;
import com.control.visitas.repository.TypeMachineRepository;
import com.control.visitas.services.interfaces.IMachineInterface;
import com.control.visitas.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MachineService implements IMachineInterface {

    private final MachineRepository machineRepository;

    private final CustomersRepository customersRepository;

    private final TypeMachineRepository typeMachineRepository;


    @Override
    public MachineResponseDTO getAllMachineByCustomer(Long customerId, int pageNumber, int pageSize) {

        MachineResponseDTO machineResponseDTO;
        PagingDataDTO pagingData;
        List<MachineDTO> machine;
        machine = machineRepository.findAllMachineByCustomerId(customerId)
                .stream()
                .map(Mapper::machineToDTO)
                .toList();
        pagingData = new PagingDataDTO(1, 10, machine.size());
        machineResponseDTO = new MachineResponseDTO(machine, pagingData);

        return machineResponseDTO;
    }

    @Override
    public MachineDTO findMachineById(Long machineId) {

        return Mapper.machineToDTO(machineRepository.getReferenceById(machineId));
    }

    @Override
    public MachineDTO saveMachine(MachineDTO machineDTO) {

        Machine machine = machineRepository.save(Machine.builder()
                        .identifier(machineDTO.getIdentifier())
                        .serialNumber(machineDTO.getSerialNumber())
                        .brand(machineDTO.getBrand())
                        .model(machineDTO.getModel())
                        .customer(customersRepository.getReferenceById(machineDTO.getCustomerId()))
                        .typeMachine(typeMachineRepository.getReferenceById(machineDTO.getTypeMachineId()))
                .build()
        );

        return Mapper.machineToDTO(machine);
    }

    @Override
    public MachineDTO updateMachine(MachineDTO machineDTO) {

        Machine machineUpdate = machineRepository.getReferenceById(machineDTO.getId());
        machineUpdate.setIdentifier(machineDTO.getIdentifier());
        machineUpdate.setSerialNumber(machineDTO.getSerialNumber());
        machineUpdate.setBrand(machineDTO.getBrand());
        machineUpdate.setModel(machineDTO.getModel());
        machineUpdate.setCustomer(customersRepository.getReferenceById(machineDTO.getCustomerId()));
        machineUpdate.setTypeMachine(typeMachineRepository.getReferenceById(machineDTO.getTypeMachineId()));

        return Mapper.machineToDTO(machineRepository.save(machineUpdate));
    }

    @Override
    public void deleteMachine(Long machineId) {

        if (!machineRepository.existsById(machineId)) {
            throw new ResourseNotFoundException("Maquina no encontrada con id: " + machineId);
        }

        machineRepository.deleteById(machineId);

    }
}
