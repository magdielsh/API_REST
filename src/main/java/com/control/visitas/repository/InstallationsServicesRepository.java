package com.control.visitas.repository;

import com.control.visitas.models.entities.InstallationsServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstallationsServicesRepository extends JpaRepository<InstallationsServices, Long> {

    InstallationsServices findInstallationsServicesByCodeInstallation(String codeInstallation);

    @Query(value = "SELECT * FROM installations_services WHERE installations_services.code_installation ILIKE %:search% ORDER BY id ASC", nativeQuery = true)
    List<InstallationsServices> filterInstallationService(@Param("search") String search);
}
