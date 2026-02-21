package com.control.visitas.dtos.technical;

import com.control.visitas.dtos.PagingDataDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TechnicalResponseDTO {

    private List<TechnicalDTO> technical;
    private PagingDataDTO pagingData;

    public TechnicalResponseDTO(List<TechnicalDTO> technicalDTOS, PagingDataDTO pagingDataDTO) {
        this.technical = technicalDTOS;
        this.pagingData = pagingDataDTO;
    }
}
