package com.control.visitas.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table(name = "type_services")
@Getter
@Setter
@RequiredArgsConstructor
public class Type_Services {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "type_service", length = 250)
    private String typeService;
}
