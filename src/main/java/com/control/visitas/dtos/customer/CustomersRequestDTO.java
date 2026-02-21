package com.control.visitas.dtos.customer;

import com.control.visitas.dtos.technical.TechnicalRequestDTO;
import com.control.visitas.util.OnCreate;
import com.control.visitas.util.OnUpdate;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomersRequestDTO {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    Long id;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El codigo no puede estar vacío")
    @Pattern(groups = {OnCreate.class, OnUpdate.class}, regexp = "^[A-Z]\\d{5}$", message = "El codigo tiene que comenzar con una letra y seguir con 5 números")
    String customerCode;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Activo no puede ser nulo")
    Boolean isEnabled;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El nombre no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El nombre no puede estar vacío")
    String name;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La Provincia no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "La Provincia no puede estar vacío")
    String province;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Las Cooerdenadas no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Las Cooerdenadas no puede estar vacias")
    String coordinates;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El email no puede ser nulo")
    @Email(groups = {OnCreate.class, OnUpdate.class}, message = "El email debe ser válido")
    String email;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El teléfono no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El teléfono no puede estar vacío")
    String landlinePhone;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El movil no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El movil no puede estar vacío")
    String mobilePhone;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La dirección no puede ser nula")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "La dirección no puede estar vacía")
    String address;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La descripción no puede ser nula")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "La descripción no puede estar vacía")
    String description;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El codigo postal no puede estar vacío")
    Long zipCode;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El horario no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El horario no puede estar vacío")
    String workSchedule;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La localidad no puede ser nula")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "La localidad no puede estar vacía")
    String locality;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El pais no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El pais no puede estar vacío")
    String country;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La páguina no puede ser nula")
    String webPage;

    TechnicalRequestDTO technical;
}
