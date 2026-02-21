package com.control.visitas.services;

import com.control.visitas.dtos.type_machine.TypeMachineDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.Type_Machine;
import com.control.visitas.repository.TypeMachineRepository;
import com.control.visitas.services.interfaces.ITypeMachineInterface;
import com.control.visitas.util.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TypeMachineService implements ITypeMachineInterface {

    private final TypeMachineRepository typeMachineRepository;

    @Override
    public List<TypeMachineDTO> getAllTypeMachine() {

        return typeMachineRepository.findAll()
                .stream()
                .map(Mapper::typeMachineToDTO)
                .toList();
    }

    @Override
    @Transactional
    public TypeMachineDTO saveTypeMachine(TypeMachineDTO typeMachineDTO) {

        Type_Machine typeMachine = typeMachineRepository.save(
                Type_Machine.builder()
                        .typeMachine(typeMachineDTO.getTypeMachine())
                        .build()
        );
        return Mapper.typeMachineToDTO(typeMachine);
    }

    @Override
    @Transactional
    public TypeMachineDTO updateTypeMachine(TypeMachineDTO typeMachineDTO) {

        Type_Machine typeMachine = typeMachineRepository.getReferenceById(typeMachineDTO.getId());
        typeMachine.setTypeMachine(typeMachineDTO.getTypeMachine());

        return Mapper.typeMachineToDTO(typeMachineRepository.save(typeMachine));
    }

    @Override
    @Transactional
    public void deleteTypeMachine(Long idTypeMachine) {

        if (!typeMachineRepository.existsById(idTypeMachine)) {
            throw new ResourseNotFoundException("Tipo de Maquina no encontrada con id: " + idTypeMachine);
        }

        typeMachineRepository.deleteById(idTypeMachine);

    }
}
