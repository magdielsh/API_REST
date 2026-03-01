package com.control.visitas.dtos;


import com.control.visitas.util.OnCreate;
import com.control.visitas.util.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstServiceMachineDTO {

    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    private Long installationServiceId;

    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    private Long machineId;


}
