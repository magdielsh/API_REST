package com.control.visitas.services;

import com.control.visitas.dtos.TypeServiceDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.Type_Services;
import com.control.visitas.repository.TypeServicesRepository;
import com.control.visitas.services.interfaces.ITypeServiceInterface;
import com.control.visitas.util.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TypeServiceService implements ITypeServiceInterface {

    private final TypeServicesRepository typeServicesRepository;

    @Override
    public List<TypeServiceDTO> findAllTypeServices() {

        return typeServicesRepository.findAll().stream()
                .map(Mapper::typeServiceToDTO)
                .toList();
    }

    @Override
    @Transactional
    public TypeServiceDTO saveTypeService(TypeServiceDTO typeServiceDTO) {

        Type_Services typeServices = typeServicesRepository.save(
                Type_Services.builder()
                        .typeService(typeServiceDTO.getTypeService())
                        .build()
        );
        return Mapper.typeServiceToDTO(typeServices);
    }

    @Override
    @Transactional
    public TypeServiceDTO updateTypeService(TypeServiceDTO typeServiceDTO) {

        Type_Services typeServices = typeServicesRepository.getReferenceById(typeServiceDTO.getId());
        typeServices.setTypeService(typeServiceDTO.getTypeService());

        return Mapper.typeServiceToDTO(typeServicesRepository.save(typeServices));
    }

    @Override
    @Transactional
    public void deleteTypeService(Long typeServiceId) {

        if (!typeServicesRepository.existsById(typeServiceId)) {
            throw new ResourseNotFoundException("Tipo de Servicio no encontrado con id: " + typeServiceId);
        }

        typeServicesRepository.deleteById(typeServiceId);

    }
}
