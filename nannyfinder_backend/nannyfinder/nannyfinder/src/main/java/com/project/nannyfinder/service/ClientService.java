package com.project.nannyfinder.service;

import com.project.nannyfinder.dto.EmployeeDetailsDTO;
import com.project.nannyfinder.dto.ReviewDTO;
import com.project.nannyfinder.dto.ServiceDTO;

import java.util.List;

public interface ClientService {
    boolean hireService(ServiceDTO serviceDto);

    EmployeeDetailsDTO getEmployeeDetailsById(Long id);

    public List<ServiceDTO> getHiringsByUserId(Long clientId);

    public boolean giveReview(ReviewDTO reviewDTO);

}
