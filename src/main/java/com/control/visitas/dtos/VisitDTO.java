package com.control.visitas.dtos;

import com.control.visitas.models.enums.StateVisit;
import com.control.visitas.util.OnCreate;
import com.control.visitas.util.OnUpdate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitDTO {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long id;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La fecha no puede ser nula")
    @FutureOrPresent(groups = {OnCreate.class, OnUpdate.class}, message = "La fecha tiene que ser actual o mayor")
    private LocalDateTime visitDate;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La hora no puede ser nula")
    private LocalTime startTime;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La hora no puede ser nula")
    private LocalTime endTime;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La descripción no puede ser nula")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "La descripción no puede estar vacía")
    private String description;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El estado de la visita no puede ser nulo")
    private StateVisit stateVisit;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La instalación no puede ser nula")
    private Long installationsServicesId;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El técnico no puede ser nulo")
    private Long technicalId;
}
