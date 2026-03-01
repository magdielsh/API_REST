package com.control.visitas.controllers;

import com.control.visitas.dtos.TypeMachineDTO;
import com.control.visitas.services.TypeMachineService;
import com.control.visitas.util.OnCreate;
import com.control.visitas.util.OnUpdate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/typeMachine")
@AllArgsConstructor
public class TypeMachineController {

    private final TypeMachineService typeMachineService;

    @GetMapping("/findAll")
    public ResponseEntity<List<TypeMachineDTO>> findAll(){

        List<TypeMachineDTO> typeMachineDTOS = typeMachineService.getAllTypeMachine();

        return ResponseEntity.ok(typeMachineDTOS);
    }

    @PostMapping("/saveTypeMachine")
    public ResponseEntity<TypeMachineDTO> saveTypeMachine(@Validated(OnCreate.class) @RequestBody TypeMachineDTO typeMachineDTO){

        TypeMachineDTO typeMachineDTOSave = typeMachineService.saveTypeMachine(typeMachineDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeMachineDTOSave);
    }

    @PutMapping("/updateTypeMachine")
    public ResponseEntity<TypeMachineDTO> updateTypeMachine(@Validated(OnUpdate.class) @RequestBody TypeMachineDTO typeMachineDTO){

        return ResponseEntity.status(HttpStatus.OK).body(typeMachineService.updateTypeMachine(typeMachineDTO));
    }

    @DeleteMapping("/deleteTypeMachine/{typeMachineId}")
    public ResponseEntity<Map<String, String>> deleteTypeMachine(@PathVariable Long typeMachineId){

        typeMachineService.deleteTypeMachine(typeMachineId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Tipo de maquina eliminada exitosamente");
        response.put("id", typeMachineId.toString());
        return ResponseEntity.ok(response);
    }
}
