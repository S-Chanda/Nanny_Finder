package com.project.nannyfinder.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewDTO {
    private Long id;
    private Date reviewDate;
    private String review;
    private Long rating;

    private Long clientId;
    private Long employeeId;
    private String clientName;

    private Long serviceId;
}
