package com.control.visitas.services.interfaces;

import com.control.visitas.dtos.installation_service.InstallationServiceDTO;
import com.control.visitas.dtos.installation_service.InstallationServiceResponseDTO;


public interface IInstallationsServiceInterface {

    InstallationServiceResponseDTO findAllInstallationService(int pageNumber, int pageSize);

    InstallationServiceResponseDTO findInstallationByCustomerId(String search, int pageNumber, int pageSize);

    InstallationServiceDTO findInstallationServiceByCode(String codeInstallation);

    InstallationServiceDTO saveInstallationService (InstallationServiceDTO installationServiceDTO);

    InstallationServiceDTO updateInstallationService(InstallationServiceDTO installationServiceDTO);

    void deleteInstallationService(Long installationServiceId);


}
