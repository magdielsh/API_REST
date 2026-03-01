package com.control.visitas.repository;

import com.control.visitas.models.entities.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visits, Long> {

    List<Visits> findVisitByInstallationsServicesId(Long installationId);
}
