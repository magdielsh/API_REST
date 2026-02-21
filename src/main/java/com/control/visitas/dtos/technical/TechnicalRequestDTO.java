package com.control.visitas.dtos.technical;

import com.control.visitas.util.OnCreate;
import com.control.visitas.util.OnUpdate;
import jakarta.validation.constraints.Email;
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
public class TechnicalRequestDTO {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    Long id;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El nombre no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El nombre no puede estar vacío")
    String name;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El email no puede ser nulo")
    @Email(groups = {OnCreate.class, OnUpdate.class}, message = "El email debe ser válido")
    String email;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El movil no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El movil no puede estar vacío")
    String mobilePhone;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La dirección no puede ser nula")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "La dirección no puede estar vacía")
    String address;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La Provincia no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "La Provincia no puede estar vacío")
    String province;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La localidad no puede ser nula")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "La localidad no puede estar vacía")
    String locality;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Las Cooerdenadas no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Las Cooerdenadas no puede estar vacias")
    String coordinates;
}
