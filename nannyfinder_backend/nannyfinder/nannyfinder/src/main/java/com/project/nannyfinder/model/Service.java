package com.project.nannyfinder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.nannyfinder.dto.ServiceDTO;
import com.project.nannyfinder.enums.ReviewStatus;
import com.project.nannyfinder.enums.ServiceStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Data
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;
    private LocalDate bookDate;
    private Long charge;
    private String location;

    private ServiceStatus serviceStatus;
    private ReviewStatus reviewStatus;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User client;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User employee;



    public ServiceDTO getServiceDto() {
        ServiceDTO dto = new ServiceDTO();
        dto.setServiceId(this.serviceId);
        dto.setBookDate(this.bookDate);
        dto.setCharge(this.charge);
        dto.setLocation(this.location);
        dto.setServiceStatus(this.serviceStatus);
        dto.setReviewStatus(this.reviewStatus);
        dto.setEmployeeId(this.employee.getId());
        dto.setClientName(this.client.getUsername());
        return dto;
    }
}
