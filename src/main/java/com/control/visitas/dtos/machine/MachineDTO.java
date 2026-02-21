package com.control.visitas.dtos.machine;

import com.control.visitas.util.OnCreate;
import com.control.visitas.util.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MachineDTO {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long id;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El identificador no puede estar vacío")
    private int identifier;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El número de serie no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El El número de serie  no puede estar vacío")
    private String serialNumber;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La marca no puede ser nula")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "La marca no puede estar vacía")
    private String brand;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El modelo no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El modelo no puede estar vacío")
    private String model;

    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    private Long customerId;

    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    private Long typeMachineId;
}
