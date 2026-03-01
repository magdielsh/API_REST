package com.control.visitas.services.interfaces;

import com.control.visitas.dtos.VisitDTO;

import java.util.List;

public interface IVisitInterface {

    List<VisitDTO> findVisitByInstallationId(Long installationId);

    VisitDTO findByVisitId(Long visitId);

    VisitDTO saveVisit(VisitDTO visitDTO);

    VisitDTO updateVisit(VisitDTO visitDTO);

    void deleteVisit(Long visitId);
}
