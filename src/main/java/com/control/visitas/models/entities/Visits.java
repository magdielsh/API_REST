package com.control.visitas.models.entities;

import com.control.visitas.models.enums.StateVisit;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "visits")
@Getter
@Setter
@RequiredArgsConstructor
public class Visits {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "visit_date", nullable = false)
    private LocalDateTime visitDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "description", nullable = false, length = 250)
    private String description;

    @Column(name = "state_visit", nullable = false)
    @Enumerated(EnumType.STRING)
    private StateVisit stateVisit;

    @ManyToOne
    @JoinColumn(name = "installation_id", referencedColumnName = "id", nullable = false)
    private Installation installation;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "visit_installations_services", joinColumns = @JoinColumn(name = "visits_id"), inverseJoinColumns = @JoinColumn(name = "installations_services_id"))
    private Set<InstallationsServices> installationsServicesVisits = new HashSet<InstallationsServices>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "visit_technical", joinColumns = @JoinColumn(name = "visits_id"), inverseJoinColumns = @JoinColumn(name = "technical_id"))
    private Set<Technical> technicalsVisit = new HashSet<Technical>();

}
