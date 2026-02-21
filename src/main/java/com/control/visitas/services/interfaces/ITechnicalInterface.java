package com.control.visitas.services.interfaces;

import com.control.visitas.dtos.technical.TechnicalDTO;
import com.control.visitas.dtos.technical.TechnicalRequestDTO;
import com.control.visitas.dtos.technical.TechnicalResponseDTO;
import com.control.visitas.models.entities.Technical;

public interface ITechnicalInterface {

    TechnicalResponseDTO findAllTechnical(int pageNumber, int pageSize);

    TechnicalResponseDTO findFilterTechnical(String search, int pageNumber, int pageSize);

    TechnicalDTO findTechnicalById (Long technicalId);

    Technical technicalById (Long technicalId);

    TechnicalDTO saveTechnical(TechnicalRequestDTO technicalRequestDTO);

    TechnicalDTO updateTechnical(TechnicalRequestDTO technicalRequestDTO);

    void deleteTechnical(Long technicalId);
}
