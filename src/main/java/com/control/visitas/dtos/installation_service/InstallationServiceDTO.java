package com.control.visitas.dtos.installation_service;

import com.control.visitas.dtos.machine.MachineDTO;
import com.control.visitas.util.OnCreate;
import com.control.visitas.util.OnUpdate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstallationServiceDTO {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long id;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El código de instalación no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El código de instalación no puede estar vacío")
    private String codeInstallation;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La fecha de inicio no puede ser nula")
    @FutureOrPresent(groups = {OnCreate.class, OnUpdate.class}, message = "La fecha tiene que ser actual o mayor")
    private LocalDateTime startDate;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La fecha de inicio no puede ser nula")
    @FutureOrPresent(groups = {OnCreate.class, OnUpdate.class}, message = "La fecha tiene que ser actual o mayor")
    private LocalDateTime endDate;

    private String finalReason;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La cantidad no puede estar vacía")
    private int quantityEquipments;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El tipo de servicio no puede ser nulo")
    private Long typeServicesId;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El cliente no puede ser nulo")
    private Long customerId;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El técnico no puede ser nulo")
    private Long technicalId;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Los equipos no pueden ser nulos")
    private List<MachineDTO> machines;
}
