package com.control.visitas.services;

import com.control.visitas.dtos.PagingDataDTO;
import com.control.visitas.dtos.installation_service.InstallationServiceDTO;
import com.control.visitas.dtos.installation_service.InstallationServiceResponseDTO;
import com.control.visitas.dtos.machine.MachineDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.InstallationsServices;
import com.control.visitas.models.entities.Machine;
import com.control.visitas.repository.*;
import com.control.visitas.services.interfaces.IInstallationsServiceInterface;
import com.control.visitas.util.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InstallationServiceService implements IInstallationsServiceInterface {

    private final InstallationsServicesRepository installationsServicesRepository;

    private final TypeServicesRepository typeServicesRepository;

    private final CustomersRepository customersRepository;

    private final TechnicalRepository technicalRepository;

    private final MachineRepository machineRepository;

    @Override
    public InstallationServiceResponseDTO findAllInstallationService(int pageNumber, int pageSize) {

        InstallationServiceResponseDTO installationServiceResponseDTO;
        PagingDataDTO pagingDataDTO;
        List<InstallationServiceDTO> installationServiceDTOS;
        installationServiceDTOS = installationsServicesRepository.findAll()
                .stream()
                .map(Mapper::installationServiceToDTO)
                .toList();
        pagingDataDTO = new PagingDataDTO(pageNumber, pageSize, installationServiceDTOS.size());
        installationServiceResponseDTO = new InstallationServiceResponseDTO(installationServiceDTOS, pagingDataDTO);

        return installationServiceResponseDTO;
    }

    @Override
    public InstallationServiceResponseDTO findInstallationByCustomerId(String search, int pageNumber, int pageSize) {

        InstallationServiceResponseDTO installationServiceResponseDTO;
        PagingDataDTO pagingDataDTO;
        List<InstallationServiceDTO> installationServiceDTOS;
        installationServiceDTOS = installationsServicesRepository.filterInstallationService(search)
                .stream()
                .map(Mapper::installationServiceToDTO)
                .toList();
        pagingDataDTO = new PagingDataDTO(pageNumber, pageSize, installationServiceDTOS.size());
        installationServiceResponseDTO = new InstallationServiceResponseDTO(installationServiceDTOS, pagingDataDTO);

        return installationServiceResponseDTO;
    }

    @Override
    public InstallationServiceDTO findInstallationServiceByCode(String codeInstallation) {

        return Mapper.installationServiceToDTO(installationsServicesRepository.findInstallationsServicesByCodeInstallation(codeInstallation));
    }

    @Override
    @Transactional
    public InstallationServiceDTO saveInstallationService(InstallationServiceDTO installationServiceDTO) {

        InstallationsServices installationsServices = installationsServicesRepository.save(
               InstallationsServices.builder()
                       .codeInstallation(installationServiceDTO.getCodeInstallation())
                       .typeServices(typeServicesRepository.getReferenceById(installationServiceDTO.getTypeServicesId()))
                       .startDate(installationServiceDTO.getStartDate())
                       .endDate(installationServiceDTO.getEndDate())
                       .finalReason(installationServiceDTO.getFinalReason())
                       .customers(customersRepository.getReferenceById(installationServiceDTO.getCustomerId()))
                       .technical(technicalRepository.getReferenceById(installationServiceDTO.getTechnicalId()))
                       .quantityEquipments(installationServiceDTO.getQuantityEquipments())
                       .machines(installationServiceDTO.getMachines()
                               .stream()
                               .map(machineDTO -> machineRepository.findById(machineDTO.getId()).get())
                               .toList()
                       )
                       .build()
        );

        return Mapper.installationServiceToDTO(installationsServices);
    }

    @Override
    @Transactional
    public InstallationServiceDTO updateInstallationService(InstallationServiceDTO installationServiceDTO) {

        if (!installationsServicesRepository.existsById(installationServiceDTO.getId())) {
            throw new ResourseNotFoundException("Instalación no encontrada con id: " + installationServiceDTO.getId());
        }

        InstallationsServices installationsServices = installationsServicesRepository.getReferenceById(installationServiceDTO.getId());
        installationsServices.setCodeInstallation(installationsServices.getCodeInstallation());
        installationsServices.setTypeServices(typeServicesRepository.getReferenceById(installationServiceDTO.getTypeServicesId()));
        installationsServices.setCustomers(customersRepository.getReferenceById(installationServiceDTO.getCustomerId()));
        installationsServices.setStartDate(installationServiceDTO.getStartDate());
        installationsServices.setEndDate(installationServiceDTO.getEndDate());
        installationsServices.setTechnical(technicalRepository.getReferenceById(installationServiceDTO.getTechnicalId()));
        installationsServices.setFinalReason(installationServiceDTO.getFinalReason());

        return Mapper.installationServiceToDTO(installationsServicesRepository.save(installationsServices));
    }

    @Override
    @Transactional
    public void deleteInstallationService(Long installationServiceId) {

        if (!installationsServicesRepository.existsById(installationServiceId)) {
            throw new ResourseNotFoundException("Instalación no encontrada con id: " + installationServiceId);
        }

        installationsServicesRepository.deleteById(installationServiceId);

    }
}
