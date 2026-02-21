package com.control.visitas.dtos.customer;

import com.control.visitas.dtos.technical.TechnicalDTO;

public record CustomersDTO(Long id, String customerCode, Boolean isEnabled, String name, String province,
                           String coordinates, String email, String landlinePhone, String mobilePhone, String address,
                           String description, Long zipCode, String workSchedule, String locality, String country,
                           String webPage, TechnicalDTO technical) {
}
