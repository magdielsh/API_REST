package com.control.visitas.services;

import com.control.visitas.dtos.IncidenceDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.Incidence;
import com.control.visitas.repository.IncidenceRepository;
import com.control.visitas.repository.InstallationsServicesRepository;
import com.control.visitas.services.interfaces.IIncidenceInterface;
import com.control.visitas.util.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IncidenceService implements IIncidenceInterface {

    private final IncidenceRepository incidenceRepository;

    private final InstallationsServicesRepository installationsServicesRepository;

    @Override
    public List<IncidenceDTO> findIncidenceByInstallation(Long installationId) {

        return incidenceRepository.findIncidenceByInstallationsServicesId(installationId)
                .stream()
                .map(Mapper::incidenceToDTO)
                .toList();
    }

    @Override
    public IncidenceDTO findIncidenceById(Long incidenceId) {

        return Mapper.incidenceToDTO(incidenceRepository.findById(incidenceId).get());
    }

    @Override
    @Transactional
    public IncidenceDTO saveIncidence(IncidenceDTO incidenceDTO) {

        Incidence incidence = incidenceRepository.save(
                Incidence.builder()
                        .incidenceCode(incidenceDTO.getIncidenceCode())
                        .incidentType(incidenceDTO.getIncidentType())
                        .description(incidenceDTO.getDescription())
                        .openingDate(incidenceDTO.getOpeningDate())
                        .closingDate(incidenceDTO.getClosingDate())
                        .isOperational(incidenceDTO.isOperational())
                        .incidenceSolution("")
                        .closedBy("")
                        .installationsServices(installationsServicesRepository.getReferenceById(incidenceDTO.getInstallationServiceId()))
                        .build()
        );

        return Mapper.incidenceToDTO(incidence);
    }

    @Override
    @Transactional
    public IncidenceDTO updateIncidence(IncidenceDTO incidenceDTO) {

        Incidence incidence = incidenceRepository.findById(incidenceDTO.getId()).get();
        incidence.setIncidenceCode(incidenceDTO.getIncidenceCode());
        incidence.setIncidentType(incidenceDTO.getIncidentType());
        incidence.setDescription(incidenceDTO.getDescription());
        incidence.setOpeningDate(incidenceDTO.getOpeningDate());
        incidence.setClosingDate(incidenceDTO.getClosingDate());
        incidence.setOperational(incidenceDTO.isOperational());
        incidence.setIncidenceSolution(incidenceDTO.getIncidenceSolution());
        incidence.setClosedBy(incidenceDTO.getClosedBy());

        return Mapper.incidenceToDTO(incidence);
    }

    @Override
    @Transactional
    public void deleteIncidence(Long incidenceId) {

        if (!incidenceRepository.existsById(incidenceId)) {
            throw new ResourseNotFoundException("Incidencia no encontrada con id: " + incidenceId);
        }

        incidenceRepository.deleteById(incidenceId);

    }
}
