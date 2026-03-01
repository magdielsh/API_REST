package com.control.visitas.repository;

import com.control.visitas.models.entities.Incidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenceRepository extends JpaRepository<Incidence, Long> {

    List<Incidence> findIncidenceByInstallationsServicesId(Long installationId);
}
