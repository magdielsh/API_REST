package com.control.visitas.dtos;

import com.control.visitas.models.enums.IncidentType;
import com.control.visitas.util.OnCreate;
import com.control.visitas.util.OnUpdate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncidenceDTO {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long id;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El código no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El código no puede estar vacío")
    private String incidenceCode;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La incidencia no puede ser nula")
    private IncidentType incidentType;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La descripción no puede ser nula")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "La descripción no puede estar vacía")
    private String description;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La fecha de inicio no puede ser nula")
    @FutureOrPresent(groups = {OnCreate.class, OnUpdate.class}, message = "La fecha tiene que ser actual o mayor")
    private LocalDateTime openingDate;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La fecha de fin no puede ser nula")
    @FutureOrPresent(groups = {OnCreate.class, OnUpdate.class}, message = "La fecha tiene que ser actual o mayor")
    private LocalDateTime closingDate;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Operativa no puede ser nulo")
    private boolean isOperational;

    private String incidenceSolution;

    private String closedBy;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La Instalación no puede ser nula")
    private Long installationServiceId;
}
