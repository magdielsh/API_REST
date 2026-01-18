package com.control.visitas.models.entities;

import com.control.visitas.models.enums.IncidentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "incidence")
@Getter
@Setter
@RequiredArgsConstructor
public class Incidence {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "incidence_code", length = 30, unique = true)
    private String incidenceCode;

    @Column(name = "incident_type")
    @Enumerated(EnumType.STRING)
    private IncidentType incidentType;

    @Column(name = "description", nullable = false, length = 250)
    private String description;

    @Column(name = "opening_date", nullable = false)
    private LocalDateTime openingDate;

    @Column(name = "closing_date")
    private LocalDateTime closingDate;

    @Column(name = "is_operational")
    private boolean isOperational;

    @Column(name = "incidence_solution", length = 250)
    private String incidenceSolution;

    @Column(name = "closed_by", length = 250)
    private String closedBy;

    @ManyToOne
    @JoinColumn(name = "installation_id", referencedColumnName = "id")
    private Installation installation;

}
