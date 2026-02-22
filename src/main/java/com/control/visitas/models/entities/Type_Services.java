package com.control.visitas.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Table(name = "type_services")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Type_Services {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "type_service", length = 250)
    private String typeService;
}
