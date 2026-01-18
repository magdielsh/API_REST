package com.control.visitas.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table(name = "type_machine")
@Getter
@Setter
@RequiredArgsConstructor
public class Type_Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "type_machine", length = 50)
    private String typeMachine;
}
