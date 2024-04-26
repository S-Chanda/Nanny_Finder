package com.project.nannyfinder.dto;

import com.project.nannyfinder.model.User;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeDetailsDTO {
    private User user;
    private List<ReviewDTO> reviewDTOList;
}


