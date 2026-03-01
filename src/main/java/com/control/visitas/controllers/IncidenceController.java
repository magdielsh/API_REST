package com.control.visitas.controllers;

import com.control.visitas.dtos.IncidenceDTO;
import com.control.visitas.services.IncidenceService;
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
@RequestMapping("/api/incidence")
@AllArgsConstructor
public class IncidenceController {

    private final IncidenceService incidenceService;

    @GetMapping("/findByInstallationId/{installationId}")
    public ResponseEntity<List<IncidenceDTO>> findAllByInstallationId(@PathVariable Long installationId){

        List<IncidenceDTO> incidenceDTO = incidenceService.findIncidenceByInstallation(installationId);

        return ResponseEntity.ok(incidenceDTO);
    }

    @GetMapping("/findById/{incidenceId}")
    public ResponseEntity<IncidenceDTO> findByIncidenceId(@PathVariable Long incidenceId){

        IncidenceDTO incidenceDTO = incidenceService.findIncidenceById(incidenceId);

        return ResponseEntity.ok(incidenceDTO);
    }

    @PostMapping("/saveIncidence")
    public ResponseEntity<IncidenceDTO> saveIncidence(@Validated(OnCreate.class) @RequestBody IncidenceDTO incidenceDTO){

        IncidenceDTO incidence = incidenceService.saveIncidence(incidenceDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(incidence);
    }

    @PutMapping("/updateIncidence")
    public ResponseEntity<IncidenceDTO> updateIncidence (@Validated(OnUpdate.class) @RequestBody IncidenceDTO incidenceDTO){

        IncidenceDTO incidence = incidenceService.updateIncidence(incidenceDTO);

        return ResponseEntity.status(HttpStatus.OK).body(incidence);
    }

    @DeleteMapping("/deleteIncidence/{incidenceId}")
    public ResponseEntity<Map<String, String>> deleteIncidence(@PathVariable Long incidenceId){

        incidenceService.deleteIncidence(incidenceId);
        Map<String, String> result = new HashMap<>();
        result.put("message", "Incidencia Eliminada");
        result.put("id", incidenceId.toString());

        return ResponseEntity.ok(result);
    }

}
