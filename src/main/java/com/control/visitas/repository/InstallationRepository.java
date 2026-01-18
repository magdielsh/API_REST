package com.control.visitas.repository;

import com.control.visitas.models.entities.Installation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallationRepository extends JpaRepository<Installation, Long> {
}
