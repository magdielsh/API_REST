package com.control.visitas.repository;

import com.control.visitas.models.entities.Type_Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeMachineRepository extends JpaRepository<Type_Machine, Long> {
}
