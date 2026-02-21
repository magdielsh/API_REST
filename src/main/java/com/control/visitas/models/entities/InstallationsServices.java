package com.control.visitas.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "installations_services")
@Getter
@Setter
@RequiredArgsConstructor
public class InstallationsServices {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "code_installation", nullable = false, length = 50, unique = true)
    private String codeInstallation;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "final_reason", length = 500)
    private String finalReason;

    @Column(name = "quantity_equipments", nullable = false)
    private int quantityEquipments;

    @ManyToOne
    @JoinColumn(name = "type_services_id", nullable = false, referencedColumnName = "id")
    private Type_Services typeServices;

    @OneToOne(targetEntity = Customers.class)
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customers customers;

    @ManyToOne
    @JoinColumn(name = "technical_id", nullable = false, referencedColumnName = "id")
    private Technical technical;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "installations_services_machine", joinColumns = @JoinColumn(name = "installations_services_id"), inverseJoinColumns = @JoinColumn(name = "machine_id"))
    private Set<Machine> machines = new HashSet<Machine>();


}
