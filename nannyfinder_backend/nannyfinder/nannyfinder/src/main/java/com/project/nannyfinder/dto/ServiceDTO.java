package com.project.nannyfinder.dto;

import com.project.nannyfinder.enums.ReviewStatus;
import com.project.nannyfinder.enums.ServiceStatus;
import lombok.Data;

import java.time.LocalDate;


@Data
public class ServiceDTO {
    private Long serviceId;
    private LocalDate bookDate;
    private Long charge;
    private String location;
    private ServiceStatus serviceStatus;
    private ReviewStatus reviewStatus;
    private Long clientId;
    private String clientName;
    private Long employeeId;



}
