package com.control.visitas.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Table(name = "type_machine")
@NoArgsConstructor
@AllArgsConstructor
public class Type_Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "type_machine", length = 50)
    private String typeMachine;
}
