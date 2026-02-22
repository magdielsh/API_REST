package com.control.visitas.services.interfaces;

import com.control.visitas.dtos.type_service.TypeServiceDTO;

import java.util.List;

public interface ITypeServiceInterface {

    List<TypeServiceDTO> findAllTypeServices();

    TypeServiceDTO saveTypeService (TypeServiceDTO typeServiceDTO);

    TypeServiceDTO updateTypeService (TypeServiceDTO typeServiceDTO);

    void deleteTypeService (Long typeServiceId);
}
