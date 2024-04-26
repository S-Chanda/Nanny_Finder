package com.project.nannyfinder.service;

import com.project.nannyfinder.dto.ServiceDTO;

import java.util.List;

public interface EmployeeService {
    List<ServiceDTO> getAllEmployeeServices(Long employeeId);

    boolean changeServiceStatus(Long serviceId, String status);


}
