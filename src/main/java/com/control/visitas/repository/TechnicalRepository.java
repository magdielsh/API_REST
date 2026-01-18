package com.control.visitas.repository;

import com.control.visitas.models.entities.Technical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalRepository extends JpaRepository<Technical, Long> {
}
