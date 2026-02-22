package com.control.visitas.controllers;

import com.control.visitas.dtos.installation_service.InstallationServiceDTO;
import com.control.visitas.dtos.installation_service.InstallationServiceResponseDTO;
import com.control.visitas.services.InstallationServiceService;
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
@RequestMapping("/api/installationService")
@AllArgsConstructor
public class InstallationServiceController {

    private final InstallationServiceService installationServiceService;

    @GetMapping("/findAll")
    public ResponseEntity<InstallationServiceResponseDTO> findAllInstallationService(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        InstallationServiceResponseDTO installationServiceResponseDTO = installationServiceService.findAllInstallationService(pageNumber, pageSize);
        return ResponseEntity.ok(installationServiceResponseDTO);
    }

    @GetMapping("/findInstallationServiceFilter")
    public ResponseEntity<InstallationServiceResponseDTO> findSilter(
            @RequestParam(name = "search") String search,
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        InstallationServiceResponseDTO installationServiceResponseDTO = installationServiceService.findInstallationByCustomerId(search, pageNumber, pageSize);
        return ResponseEntity.ok(installationServiceResponseDTO);
    }

    @GetMapping("/findByCodeInstallation/{codeInstallation}")
    public ResponseEntity<InstallationServiceDTO> findByCodeInstallation (@PathVariable String codeInstallation){

        return ResponseEntity.ok(installationServiceService.findInstallationServiceByCode(codeInstallation));
    }

    @PostMapping("/saveInstallationService")
    public ResponseEntity<InstallationServiceDTO> saveInstallationService(@Validated(OnCreate.class) @RequestBody InstallationServiceDTO installationServiceDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(installationServiceService.saveInstallationService(installationServiceDTO));
    }

    @PutMapping("/updateInstallationService")
    public ResponseEntity<InstallationServiceDTO> updateInstallationService(@Validated(OnUpdate.class) @RequestBody InstallationServiceDTO installationServiceDTO){

        return ResponseEntity.ok(installationServiceService.updateInstallationService(installationServiceDTO));
    }

    @DeleteMapping("/deleteInstallationService/{installationId}")
    public ResponseEntity<Map<String, String>> deleteInstallationService(@PathVariable Long installationId){

        installationServiceService.deleteInstallationService(installationId);
        Map<String, String> result = new HashMap<>();
        result.put("message", "Instalación Eliminada");
        result.put("id", installationId.toString());

        return ResponseEntity.ok(result);
    }

}
