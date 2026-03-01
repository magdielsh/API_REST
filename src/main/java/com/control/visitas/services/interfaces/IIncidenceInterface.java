package com.control.visitas.services.interfaces;

import com.control.visitas.dtos.IncidenceDTO;

import java.util.List;

public interface IIncidenceInterface {

     List<IncidenceDTO> findIncidenceByInstallation(Long installationId);

     IncidenceDTO findIncidenceById(Long incidenceId);

     IncidenceDTO saveIncidence(IncidenceDTO incidenceDTO);

     IncidenceDTO updateIncidence(IncidenceDTO incidenceDTO);

     void deleteIncidence(Long incidenceId);
}
