package com.control.visitas.controllers;

import com.control.visitas.dtos.TypeServiceDTO;
import com.control.visitas.services.TypeServiceService;
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
@RequestMapping("/api/typeService")
@AllArgsConstructor
public class TypeServiceController {

    private final TypeServiceService typeServiceService;

    @GetMapping("/findAll")
    public ResponseEntity<List<TypeServiceDTO>> findAllTypeService (){

        return ResponseEntity.status(HttpStatus.OK).body(typeServiceService.findAllTypeServices());

    }

    @PostMapping("/saveTypeService")
    public ResponseEntity<TypeServiceDTO> saveTypeService(@Validated(OnCreate.class) @RequestBody TypeServiceDTO typeServiceDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(typeServiceService.saveTypeService(typeServiceDTO));

    }

    @PutMapping("/updateTypeService")
    public ResponseEntity<TypeServiceDTO> updateTypeService(@Validated(OnUpdate.class) @RequestBody TypeServiceDTO typeServiceDTO){

        return ResponseEntity.status(HttpStatus.OK).body(typeServiceService.updateTypeService(typeServiceDTO));

    }

    @DeleteMapping("/deleteTypeService/{typeServiceId}")
    public ResponseEntity<Map<String, String>> deleteTypeService(@PathVariable Long typeServiceId){

        typeServiceService.deleteTypeService(typeServiceId);
        Map<String, String> result = new HashMap<>();
        result.put("message", "Tipo de Servicio Eliminado");
        result.put("id", typeServiceId.toString());

        return ResponseEntity.ok(result);

    }
}
