package com.control.visitas.dtos;

public record TechnicalResponse(Long id, String name, String email, String mobilePhone, String address,
                                String province, String locality, String coordinates) {
}
