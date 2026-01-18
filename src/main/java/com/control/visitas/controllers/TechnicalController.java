package com.control.visitas.controllers;

import com.control.visitas.dtos.TechnicalRequestDTO;
import com.control.visitas.dtos.TechnicalResponse;
import com.control.visitas.models.entities.Technical;
import com.control.visitas.services.TechnicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("technical")
public class TechnicalController {

    private final TechnicalService technicalService;

    @Autowired
    public TechnicalController(TechnicalService technicalService) {
        this.technicalService = technicalService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TechnicalResponse>> findAll(){
        List<TechnicalResponse> technicalResponses = technicalService.getAllTechnicals()
                .stream()
                .map(this::getTechnicalResponse).toList();

        return ResponseEntity.ok(technicalResponses);
    }

    @GetMapping("find/{technicalId}")
    public ResponseEntity<TechnicalResponse> findTechnicalById(@PathVariable Long technicalId){
        TechnicalResponse technicalResponse = getTechnicalResponse(technicalService.getTechnicalByID(technicalId));

        return ResponseEntity.ok(technicalResponse);
    }

    @PostMapping("/saveTechnical")
    public ResponseEntity<String> saveTechnical(@RequestBody TechnicalRequestDTO technicalRequestDTO){
        if(technicalRequestDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }

        technicalService.saveTechnical(Technical.builder()
                        .name(technicalRequestDTO.getName())
                        .email(technicalRequestDTO.getEmail())
                        .mobilePhone(technicalRequestDTO.getMobilePhone())
                        .address(technicalRequestDTO.getAddress())
                        .province(technicalRequestDTO.getProvince())
                        .locality(technicalRequestDTO.getLocality())
                        .coordinates(technicalRequestDTO.getCoordinates())
                        .build());

        return ResponseEntity.ok("Technical Created");
    }

    @DeleteMapping("deleteTechnical/{technicalId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long technicalId){

        if(technicalId != null){
            technicalService.deleteTechnicalById(technicalId);
            return ResponseEntity.ok("Registro Eliminado");
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/updateTechnical")
    public ResponseEntity<String> updateTechnical(@RequestBody TechnicalRequestDTO technicalRequestDTO){
        if(technicalRequestDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        Technical technicalUpdate = technicalService.getTechnicalByID(technicalRequestDTO.getId());

        technicalUpdate.setName(technicalRequestDTO.getName());
        technicalUpdate.setEmail(technicalRequestDTO.getEmail());
        technicalUpdate.setMobilePhone(technicalRequestDTO.getMobilePhone());
        technicalUpdate.setAddress(technicalRequestDTO.getAddress());
        technicalUpdate.setProvince(technicalRequestDTO.getProvince());
        technicalUpdate.setLocality(technicalRequestDTO.getLocality());
        technicalUpdate.setCoordinates(technicalRequestDTO.getCoordinates());

        technicalService.updateTechnical(technicalUpdate);

        return ResponseEntity.ok("Customer Updated");
    }

    private TechnicalResponse getTechnicalResponse(Technical technical) {
            return new TechnicalResponse(
                    technical.getId(),
                    technical.getName(),
                    technical.getEmail(),
                    technical.getMobilePhone(),
                    technical.getAddress(),
                    technical.getProvince(),
                    technical.getLocality(),
                    technical.getCoordinates());
    }
}
