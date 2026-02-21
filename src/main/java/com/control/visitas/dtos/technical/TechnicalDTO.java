package com.control.visitas.dtos.technical;

public record TechnicalDTO(Long id, String name, String email, String mobilePhone, String address,
                           String province, String locality, String coordinates) {
}
