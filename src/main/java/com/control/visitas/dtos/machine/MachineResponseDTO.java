package com.control.visitas.dtos.machine;

import com.control.visitas.dtos.PagingDataDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MachineResponseDTO {

    private List<MachineDTO> machineDTOS;
    private PagingDataDTO pagingDataDTO;

    public MachineResponseDTO(List<MachineDTO> machineDTOS, PagingDataDTO pagingDataDTO) {
        this.machineDTOS = machineDTOS;
        this.pagingDataDTO = pagingDataDTO;
    }
}
