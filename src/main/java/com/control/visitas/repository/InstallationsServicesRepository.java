package com.control.visitas.repository;

import com.control.visitas.models.entities.InstallationsServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallationsServicesRepository extends JpaRepository<InstallationsServices, Long> {
}
