package com.control.visitas.repository;

import com.control.visitas.models.entities.Customers;
import com.control.visitas.models.entities.Technical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TechnicalRepository extends JpaRepository<Technical, Long> {

    @Query(value = "SELECT * FROM technical WHERE technical.name ILIKE %:search% OR technical.email ILIKE %:search% OR technical.mobile_phone ILIKE %:search% ORDER BY id ASC", nativeQuery = true)
    List<Technical> filterTechnical(@Param("search") String search);
}
