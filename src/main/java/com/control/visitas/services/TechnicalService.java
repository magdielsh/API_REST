package com.control.visitas.services;

import com.control.visitas.models.entities.Technical;
import com.control.visitas.repository.TechnicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicalService {

    @Autowired
    private TechnicalRepository technicalRepository;

    public List<Technical> getAllTechnicals (){
        return technicalRepository.findAll();
    }

    public Technical getTechnicalByID (Long id){
        return technicalRepository.getReferenceById(id);
    }

    public void saveTechnical (Technical technical){
        technicalRepository.save(technical);
    }

    public void updateTechnical(Technical technical){
        technicalRepository.save(technical);
    }

    public void deleteTechnicalById(Long technicalId){
        technicalRepository.deleteById(technicalId);
    }
}
