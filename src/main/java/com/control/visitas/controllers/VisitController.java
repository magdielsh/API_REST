package com.control.visitas.controllers;

import com.control.visitas.dtos.VisitDTO;
import com.control.visitas.services.VisitService;
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
@RequestMapping("/api/visit")
@AllArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @GetMapping("/findByInstallationId/{installationId}")
    public ResponseEntity<List<VisitDTO>> findByInstallatioId(@PathVariable Long installationId){

        return ResponseEntity.ok(visitService.findVisitByInstallationId(installationId));
    }

    @GetMapping("/findByVisitId/{visitId}")
    public ResponseEntity<VisitDTO> findByVisitId(@PathVariable Long visitId){

        return ResponseEntity.ok(visitService.findByVisitId(visitId));
    }

    @PostMapping("/saveVisit")
    public ResponseEntity<VisitDTO> saveVisit(@Validated(OnCreate.class) @RequestBody VisitDTO visitDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(visitService.saveVisit(visitDTO));
    }

    @PutMapping("/updateVisit")
    public ResponseEntity<VisitDTO> updateVisit(@Validated(OnUpdate.class) @RequestBody VisitDTO visitDTO){

        return ResponseEntity.status(HttpStatus.OK).body(visitService.updateVisit(visitDTO));
    }

    @DeleteMapping("/deleteVisit/{visitId}")
    public ResponseEntity<Map<String, String>> deleteVisit(@PathVariable Long visitId){

        visitService.deleteVisit(visitId);
        Map<String, String> result = new HashMap<>();
        result.put("message", "Visita Eliminada");
        result.put("id", visitId.toString());

        return ResponseEntity.ok(result);
    }
}
