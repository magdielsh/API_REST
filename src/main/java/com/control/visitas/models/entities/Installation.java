package com.control.visitas.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table(name = "installation")
@Getter
@Setter
@RequiredArgsConstructor
public class Installation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "code_installation", nullable = false, length = 50, unique = true)
    private String codeInstallation;

    @OneToOne(targetEntity = Customers.class)
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customers customers;

}
