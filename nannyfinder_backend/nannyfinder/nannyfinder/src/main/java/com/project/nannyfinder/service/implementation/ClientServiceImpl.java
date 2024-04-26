package com.project.nannyfinder.service.implementation;

import com.project.nannyfinder.dto.EmployeeDetailsDTO;
import com.project.nannyfinder.dto.ReviewDTO;
import com.project.nannyfinder.dto.ServiceDTO;
import com.project.nannyfinder.enums.ReviewStatus;
import com.project.nannyfinder.enums.ServiceStatus;
import com.project.nannyfinder.model.Review;
import com.project.nannyfinder.model.User;
import com.project.nannyfinder.repository.ReviewRepository;
import com.project.nannyfinder.repository.ServiceRepository;
import com.project.nannyfinder.repository.UserRepository;
import com.project.nannyfinder.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public boolean hireService(ServiceDTO serviceDto) {
        Optional<User> client = this.userRepository.findById(serviceDto.getClientId());
        Optional<User> employee = this.userRepository.findById(serviceDto.getEmployeeId());
        if (client.isPresent() && employee.isPresent()) {
            com.project.nannyfinder.model.Service service = new com.project.nannyfinder.model.Service();

            service.setBookDate(serviceDto.getBookDate());
            service.setCharge(serviceDto.getCharge());
            service.setLocation(serviceDto.getLocation());
            service.setServiceStatus(ServiceStatus.PENDING);
            service.setClient(client.get());


            service.setEmployee(employee.get());
            service.setReviewStatus(ReviewStatus.FALSE);

            this.serviceRepository.save(service);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public EmployeeDetailsDTO getEmployeeDetailsById(Long id) {
        Optional<User> employee = this.userRepository.findById(id);
        EmployeeDetailsDTO employeeDetailsDto = new EmployeeDetailsDTO();
        if (employee.isPresent()) {
            employeeDetailsDto.setUser(employee.get());

            List<Review> reviewList = reviewRepository.findAllById(id);

            employeeDetailsDto.setReviewDTOList(reviewList.stream().map(Review::getDTO).collect(Collectors.toList()));
        }

        return employeeDetailsDto;
    }

    public List<ServiceDTO> getHiringsByUserId(Long clientId){
        return serviceRepository.findAllByClientId(clientId).stream().map(com.project.nannyfinder.model.Service::getServiceDto).collect(Collectors.toList());
    }

    public boolean giveReview(ReviewDTO reviewDTO){
        Optional<User> client = userRepository.findById(reviewDTO.getClientId());
        Optional<com.project.nannyfinder.model.Service> service = serviceRepository.findById(reviewDTO.getServiceId());

        if (client.isPresent() && service.isPresent()) {

            Review review = new Review();

            review.setReviewDate(new Date());
            review.setReview((reviewDTO.getReview()));
            review.setRating(reviewDTO.getRating());
            review.setClient(client.get());
            review.setEmployee(service.get().getEmployee());

            reviewRepository.save(review);

            com.project.nannyfinder.model.Service booking = service.get();
            booking.setReviewStatus(ReviewStatus.TRUE);

            serviceRepository.save(booking);
            return true;
        } else {
            return false;
        }
    }
}
