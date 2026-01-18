package com.control.visitas.repository;

import com.control.visitas.models.entities.Type_Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeServicesRepository extends JpaRepository<Type_Services, Long> {
}
