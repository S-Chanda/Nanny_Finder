package com.project.nannyfinder.service.implementation;

import com.project.nannyfinder.dto.ServiceDTO;
import com.project.nannyfinder.enums.ServiceStatus;
import com.project.nannyfinder.repository.ServiceRepository;
import com.project.nannyfinder.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<ServiceDTO> getAllEmployeeServices(Long employeeId) {

            return serviceRepository.findAllByEmployeeId(employeeId).stream().map(com.project.nannyfinder.model.Service::getServiceDto).collect(Collectors.toList());

    }

    @Override
    public boolean changeServiceStatus(Long serviceId, String status) {
        Optional<com.project.nannyfinder.model.Service> service = serviceRepository.findById(serviceId);
        if (service.isPresent()) {
            com.project.nannyfinder.model.Service existingService = (com.project.nannyfinder.model.Service)service.get();
            if (Objects.equals(status, "Approve")) {
                existingService.setServiceStatus(ServiceStatus.APPROVED);
            } else {
                existingService.setServiceStatus(ServiceStatus.REJECTED);
            }

            serviceRepository.save(existingService);
            return true;
        } else {
            return false;
        }
    }
}
