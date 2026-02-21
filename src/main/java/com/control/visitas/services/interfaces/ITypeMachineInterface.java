package com.control.visitas.services.interfaces;

import com.control.visitas.dtos.type_machine.TypeMachineDTO;

import java.util.List;

public interface ITypeMachineInterface {

    List<TypeMachineDTO> getAllTypeMachine ();

    TypeMachineDTO saveTypeMachine (TypeMachineDTO typeMachineDTO);

    TypeMachineDTO updateTypeMachine (TypeMachineDTO typeMachineDTO);

    void deleteTypeMachine(Long idTypeMachine);
}
