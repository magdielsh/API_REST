package com.control.visitas.dtos;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalRequestDTO {

    Long id;
    String name;
    String email;
    String mobilePhone;
    String address;
    String province;
    String locality;
    String coordinates;
}
