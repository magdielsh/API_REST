package com.control.visitas.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table(name = "machine")
@Getter
@Setter
@RequiredArgsConstructor
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "identifier", nullable = false)
    private int identifier;

    @Column(name = "serial_number", nullable = false, length = 50, unique = true)
    private String serialNumber;

    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    @Column(name = "model", nullable = false, length = 50)
    private String model;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
    private Customers customer;

    @ManyToOne
    @JoinColumn(name = "type_machine_id", nullable = false, referencedColumnName = "id")
    private Type_Machine typeMachine;

}
