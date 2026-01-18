package com.control.visitas.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "technical")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Technical {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "email", nullable = false, length = 250)
    private String email;

    @Column(name = "mobile_phone", nullable = false, length = 15)
    private String mobilePhone;

    @Column(name = "address", nullable = false, length = 250)
    private String address;

    @Column(name = "province", nullable = false, length = 250)
    private String province;

    @Column(name = "locality", nullable = false, length = 250)
    private String locality;

    @Column(name = "coordinates", nullable = false, length = 250)
    private String coordinates;
}
