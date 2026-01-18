package com.control.visitas.dtos;

import com.control.visitas.models.entities.Technical;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomersRequestDTO {

    Long id;
    String customerCode;
    Boolean isEnabled;
    String name;
    String province;
    String coordinates;
    String email;
    String landlinePhone;
    String mobilePhone;
    String address;
    String description;
    Long zipCode;
    String workSchedule;
    String locality;
    String country;
    String webPage;
    TechnicalRequestDTO technical;
}
