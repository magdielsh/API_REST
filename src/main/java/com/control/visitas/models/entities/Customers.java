package com.control.visitas.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "customers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "customer_code", length = 30, unique = true)
    private String customerCode;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "province", nullable = false, length = 250)
    private String province;

    @Column(name = "coordinates", nullable = false, length = 250)
    private String coordinates;

    @Column(name = "email", nullable = false, length = 250)
    private String email;

    @Column(name = "landline_phone", nullable = false, length = 15)
    private String landlinePhone;

    @Column(name = "mobile_phone", length = 15)
    private String mobilePhone;

    @Column(name = "address", nullable = false, length = 250)
    private String address;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "zip_code", nullable = false, length = 250)
    private Long zipCode;

    @Column(name = "work_schedule", nullable = false, length = 250)
    private String workSchedule;

    @Column(name = "locality", nullable = false, length = 250)
    private String locality;

    @Column(name = "country", nullable = false, length = 250)
    private String country;

    @Column(name = "web_page", length = 250)
    private String webPage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "technical_id", referencedColumnName = "id")
    private Technical technical;

}
