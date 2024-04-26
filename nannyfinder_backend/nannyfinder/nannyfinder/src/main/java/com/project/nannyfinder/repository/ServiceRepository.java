package com.project.nannyfinder.repository;

import com.project.nannyfinder.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository <Service, Long> {
    List<Service> findAllByEmployeeId(Long employeeId);

    List<Service> findAllByClientId(Long clientId);
}
