package com.control.visitas.repository;

import com.control.visitas.models.entities.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visits, Long> {
}
