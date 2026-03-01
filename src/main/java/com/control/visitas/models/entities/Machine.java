package com.control.visitas.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "machine")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToMany(mappedBy = "machines") // Lado no propietario
    private List<InstallationsServices> installationsServices = new ArrayList<>();

}
