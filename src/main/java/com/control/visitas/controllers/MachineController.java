package com.control.visitas.controllers;

import com.control.visitas.dtos.machine.MachineDTO;
import com.control.visitas.dtos.machine.MachineResponseDTO;
import com.control.visitas.services.MachineService;
import com.control.visitas.util.OnCreate;
import com.control.visitas.util.OnUpdate;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/machine")
@AllArgsConstructor
public class MachineController {

    private final MachineService machineService;

    @GetMapping("/findMachineByCustomer")
    public ResponseEntity<MachineResponseDTO> findMachineByCustomer(
            @RequestParam(name = "customerId") Long customerId,
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "50") int pageSize
    ){
        MachineResponseDTO machineResponseDTO = machineService.getAllMachineByCustomer(customerId, pageNumber, pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(machineResponseDTO);
    }

    @GetMapping("/findMachineById/{machineId}")
    public ResponseEntity<MachineDTO> findMachineById(@PathVariable Long machineId){

        MachineDTO machineDTO = machineService.findMachineById(machineId);

        return ResponseEntity.status(HttpStatus.OK).body(machineDTO);
    }

    @PostMapping("/saveMachine")
    @Transactional
    public ResponseEntity<MachineDTO> saveMachine(@Validated(OnCreate.class) @RequestBody MachineDTO machineDTO){

        MachineDTO machineDTOSave = machineService.saveMachine(machineDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(machineDTO);
    }

        @PutMapping("/updateMachine")
    @Transactional
    public ResponseEntity<MachineDTO> updateMachine (@Validated(OnUpdate.class) @RequestBody MachineDTO machineDTO){

        MachineDTO machineDTOUpdate = machineService.updateMachine(machineDTO);

        return ResponseEntity.status(HttpStatus.OK).body(machineDTO);
    }

    @DeleteMapping("/deleteMachine/{machineId}")
    @Transactional
    public ResponseEntity<Map<String, String>> deleteMachine(@PathVariable Long machineId){

        machineService.deleteMachine(machineId);
        HashMap<String, String> result = new HashMap<>();
        result.put("id", machineId.toString());
        result.put("message", "Maquina Eliminada");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
