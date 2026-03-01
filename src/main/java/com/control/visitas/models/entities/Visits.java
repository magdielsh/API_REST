package com.control.visitas.models.entities;

import com.control.visitas.models.enums.StateVisit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Builder
@Table(name = "visits")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinColumn(name = "installations_services_id", nullable = false, referencedColumnName = "id")
    private InstallationsServices installationsServices;

    @ManyToOne
    @JoinColumn(name = "technical_id", nullable = false, referencedColumnName = "id")
    private Technical technical;
}
