package com.control.visitas.dtos.installation_service;

import com.control.visitas.dtos.PagingDataDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InstallationServiceResponseDTO {

    List<InstallationServiceDTO> installationService;
    PagingDataDTO pagingData;

    public InstallationServiceResponseDTO(List<InstallationServiceDTO> installationServiceDTOS, PagingDataDTO pagingDataDTO) {
        this.installationService = installationServiceDTOS;
        this.pagingData = pagingDataDTO;
    }
}
