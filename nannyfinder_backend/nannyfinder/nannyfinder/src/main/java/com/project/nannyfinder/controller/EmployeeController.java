package com.project.nannyfinder.controller;

import com.project.nannyfinder.dto.ServiceDTO;
import com.project.nannyfinder.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/hire/{employeeId}")
    public ResponseEntity<List<ServiceDTO>> getAllEmployeeServices(@PathVariable Long employeeId) {
        return ResponseEntity.ok(this.employeeService.getAllEmployeeServices(employeeId));
    }

    @GetMapping("/hire/{serviceId}/{status}")
    public ResponseEntity<?> changeHiringStatus(@PathVariable Long serviceId,
                                                @PathVariable String status) {
        boolean success = employeeService.changeServiceStatus(serviceId, status);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
