package com.control.visitas.repository;

import com.control.visitas.models.entities.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long> {

    Customers findCustomersByCustomerCode(String customerCode);

    Optional<Customers> findByEmail(String email);

    List<Customers> findAllByTechnicalId(Long technicalId);

    void deleteByCustomerCode (String customerCode);

    boolean existsByCustomerCode(String customerCode);

    @Query(value = "SELECT * FROM customers WHERE customers.name ILIKE %:search% OR customers.email ILIKE %:search% OR customers.mobile_phone ILIKE %:search% ORDER BY id ASC", nativeQuery = true)
    List<Customers> filterCustomers(@Param("search") String search);


}
