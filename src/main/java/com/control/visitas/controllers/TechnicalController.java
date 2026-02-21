package com.control.visitas.controllers;

import com.control.visitas.dtos.technical.TechnicalRequestDTO;
import com.control.visitas.dtos.technical.TechnicalDTO;
import com.control.visitas.dtos.technical.TechnicalResponseDTO;
import com.control.visitas.services.TechnicalService;
import com.control.visitas.util.OnCreate;
import com.control.visitas.util.OnUpdate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("api/technical")
@AllArgsConstructor
public class TechnicalController {

    private final TechnicalService technicalService;

    @GetMapping("/findAll")
    public ResponseEntity<TechnicalResponseDTO> findAll(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "50") int pageSize
    ){
        TechnicalResponseDTO technicalResponseDTO = technicalService.findAllTechnical(pageNumber, pageSize);

        return ResponseEntity.ok(technicalResponseDTO);
    }

    @GetMapping("/findFilterTechnical")
    public ResponseEntity<TechnicalResponseDTO> findFilterTechnical(
            @RequestParam(name = "search", defaultValue = " ") String search,
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "50") int pageSize
    ){
        TechnicalResponseDTO technicalResponseDTO = technicalService.findFilterTechnical(search, pageNumber, pageSize);

        return ResponseEntity.ok(technicalResponseDTO);
    }

    @GetMapping("find/{technicalId}")
    public ResponseEntity<TechnicalDTO> findTechnicalById(@PathVariable Long technicalId){

        TechnicalDTO technicalDTO = technicalService.findTechnicalById(technicalId);

        return ResponseEntity.ok(technicalDTO);
    }

    @PostMapping("/saveTechnical")
    public ResponseEntity<TechnicalDTO> saveTechnical(@Validated(OnCreate.class) @RequestBody TechnicalRequestDTO technicalRequestDTO){

        TechnicalDTO technicalDTO = technicalService.saveTechnical(technicalRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(technicalDTO);
    }

    @PutMapping("/updateTechnical")
    public ResponseEntity<TechnicalDTO> updateTechnical(@Validated(OnUpdate.class) @RequestBody TechnicalRequestDTO technicalRequestDTO){

        return ResponseEntity.ok(technicalService.updateTechnical(technicalRequestDTO));
    }

    @DeleteMapping("deleteTechnical/{technicalId}")
    public ResponseEntity<Map<String, String>> deleteTechnical(@PathVariable Long technicalId){

        technicalService.deleteTechnical(technicalId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Técnico eliminado exitosamente");
        response.put("id", technicalId.toString());
        return ResponseEntity.ok(response);
    }
}
