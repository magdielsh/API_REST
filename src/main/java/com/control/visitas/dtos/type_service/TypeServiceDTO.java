package com.control.visitas.dtos.type_service;

import com.control.visitas.util.OnCreate;
import com.control.visitas.util.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeServiceDTO {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long id;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El Tipo de servicio no puede ser nulo")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El Tipo de servicio no puede estar vacío")
    @Pattern(groups = {OnCreate.class, OnUpdate.class}, regexp = "^[A-Za-z]+$", message = "El tipo tiene que ser un String")
    private String typeService;
}
