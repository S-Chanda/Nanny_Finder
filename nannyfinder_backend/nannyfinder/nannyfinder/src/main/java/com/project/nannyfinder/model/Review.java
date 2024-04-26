package com.project.nannyfinder.model;

import com.project.nannyfinder.dto.ReviewDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date reviewDate;
    private String review;
    private Long rating;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User client;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="employee_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User employee;

    public ReviewDTO getDTO(){
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setId(id);
        reviewDTO.setReview(review);
        reviewDTO.setRating(rating);
        reviewDTO.setReviewDate(reviewDate);
        reviewDTO.setClientId(client.getId());
        reviewDTO.setClientName(client.getUsername());
        reviewDTO.setEmployeeId(employee.getId());

        return reviewDTO;

    }

}
