package com.control.visitas.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CustomersResponseDTO {
    private List<CustomersDTO> customers;
    private PagingDataDTO pagingData;

    public CustomersResponseDTO(List<CustomersDTO> customers, PagingDataDTO pagingData) {
        this.customers = customers;
        this.pagingData = pagingData;
    }



}
