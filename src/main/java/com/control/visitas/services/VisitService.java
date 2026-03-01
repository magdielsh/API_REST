package com.control.visitas.services;

import com.control.visitas.dtos.VisitDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.Visits;
import com.control.visitas.repository.InstallationsServicesRepository;
import com.control.visitas.repository.TechnicalRepository;
import com.control.visitas.repository.VisitRepository;
import com.control.visitas.services.interfaces.IVisitInterface;
import com.control.visitas.util.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VisitService implements IVisitInterface {

    private final VisitRepository visitRepository;

    private final InstallationsServicesRepository installationsServicesRepository;

    private final TechnicalRepository technicalRepository;

    @Override
    public List<VisitDTO> findVisitByInstallationId(Long installationId) {

        return visitRepository.findVisitByInstallationsServicesId(installationId)
                .stream()
                .map(Mapper::visitToDTO)
                .toList();
    }

    @Override
    public VisitDTO findByVisitId(Long visitId) {

        return Mapper.visitToDTO(visitRepository.findById(visitId).get());
    }

    @Override
    @Transactional
    public VisitDTO saveVisit(VisitDTO visitDTO) {

        Visits visits = visitRepository.save(
                Visits.builder()
                        .visitDate(visitDTO.getVisitDate())
                        .startTime(visitDTO.getStartTime())
                        .endTime(visitDTO.getEndTime())
                        .description(visitDTO.getDescription())
                        .stateVisit(visitDTO.getStateVisit())
                        .installationsServices(installationsServicesRepository.findById(visitDTO.getInstallationsServicesId()).get())
                        .technical(technicalRepository.findById(visitDTO.getTechnicalId()).get())
                        .build()
        );

        return Mapper.visitToDTO(visits);
    }

    @Override
    @Transactional
    public VisitDTO updateVisit(VisitDTO visitDTO) {

        Visits visits = visitRepository.findById(visitDTO.getId()).get();
        visits.setVisitDate(visitDTO.getVisitDate());
        visits.setStartTime(visitDTO.getStartTime());
        visits.setEndTime(visitDTO.getEndTime());
        visits.setDescription(visitDTO.getDescription());
        visits.setStateVisit(visitDTO.getStateVisit());

        return Mapper.visitToDTO(visits);
    }

    @Override
    @Transactional
    public void deleteVisit(Long visitId) {

        if (!visitRepository.existsById(visitId)) {
            throw new ResourseNotFoundException("Visita no encontrada con id: " + visitId);
        }

        visitRepository.deleteById(visitId);
    }
}
