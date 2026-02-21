package com.control.visitas.services;

import com.control.visitas.dtos.*;
import com.control.visitas.dtos.technical.TechnicalDTO;
import com.control.visitas.dtos.technical.TechnicalRequestDTO;
import com.control.visitas.dtos.technical.TechnicalResponseDTO;
import com.control.visitas.exceptions.ResourseNotFoundException;
import com.control.visitas.models.entities.Technical;
import com.control.visitas.repository.TechnicalRepository;
import com.control.visitas.services.interfaces.ITechnicalInterface;
import com.control.visitas.util.Mapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TechnicalService implements ITechnicalInterface {


    private final TechnicalRepository technicalRepository;

    @Override
    public TechnicalResponseDTO findAllTechnical(int pageNumber, int pageSize) {
        TechnicalResponseDTO technicalResponseDTO;
        PagingDataDTO pagingDataDTO;
        List<TechnicalDTO> technicalDTOS;
        technicalDTOS = technicalRepository.findAll()
                .stream()
                .map(Mapper::technicalToDTO)
                .toList();
        pagingDataDTO = new PagingDataDTO(pageNumber, pageSize, technicalDTOS.size());
        technicalResponseDTO = new TechnicalResponseDTO(technicalDTOS, pagingDataDTO);

        return technicalResponseDTO;
    }

    @Override
    public TechnicalResponseDTO findFilterTechnical(String search, int pageNumber, int pageSize) {
        TechnicalResponseDTO technicalResponseDTO;
        PagingDataDTO pagingDataDTO;
        List<TechnicalDTO> technicalDTOS;
        technicalDTOS = technicalRepository.filterTechnical(search)
                .stream()
                .map(Mapper::technicalToDTO)
                .toList();
        pagingDataDTO = new PagingDataDTO(pageNumber, pageSize, technicalDTOS.size());
        technicalResponseDTO = new TechnicalResponseDTO(technicalDTOS, pagingDataDTO);

        return technicalResponseDTO;
    }

    @Override
    public TechnicalDTO findTechnicalById(Long technicalId) {

        return Mapper.technicalToDTO(technicalRepository.getReferenceById(technicalId));
    }

    @Override
    public Technical technicalById(Long technicalId) {

        if(technicalRepository.existsById(technicalId)) return technicalRepository.getReferenceById(technicalId);

        throw new EntityNotFoundException("Técnico con id: " + technicalId + " no encontrado");
    }

    @Override
    @Transactional
    public TechnicalDTO saveTechnical(TechnicalRequestDTO technicalRequestDTO) {

        Technical technical;
        technical = technicalRepository.save(
                Technical.builder()
                        .name(technicalRequestDTO.getName())
                        .email(technicalRequestDTO.getEmail())
                        .mobilePhone(technicalRequestDTO.getMobilePhone())
                        .address(technicalRequestDTO.getAddress())
                        .province(technicalRequestDTO.getProvince())
                        .locality(technicalRequestDTO.getLocality())
                        .coordinates(technicalRequestDTO.getCoordinates())
                        .build()
        );

        return Mapper.technicalToDTO(technical);
    }

    @Override
    @Transactional
    public TechnicalDTO updateTechnical(TechnicalRequestDTO technicalRequestDTO) {

        Technical technical = technicalRepository.getReferenceById(technicalRequestDTO.getId());
        technical.setName(technicalRequestDTO.getName());
        technical.setEmail(technicalRequestDTO.getEmail());
        technical.setMobilePhone(technicalRequestDTO.getMobilePhone());
        technical.setAddress(technicalRequestDTO.getAddress());
        technical.setProvince(technicalRequestDTO.getProvince());
        technical.setLocality(technicalRequestDTO.getLocality());
        technical.setCoordinates(technicalRequestDTO.getCoordinates());

        return Mapper.technicalToDTO(technicalRepository.save(technical));
    }

    @Override
    @Transactional
    public void deleteTechnical(Long technicalId) {

        if (!technicalRepository.existsById(technicalId)) {
            throw new ResourseNotFoundException("Técnico no encontrado con id: " + technicalId);
        }

        technicalRepository.deleteById(technicalId);

    }
}
