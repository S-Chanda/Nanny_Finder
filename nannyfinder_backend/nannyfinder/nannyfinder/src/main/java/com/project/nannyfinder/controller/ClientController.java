package com.project.nannyfinder.controller;

import com.project.nannyfinder.dto.ReviewDTO;
import com.project.nannyfinder.dto.ServiceDTO;
import com.project.nannyfinder.service.ClientService;
import com.project.nannyfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;
    @GetMapping("/search/{username}")
    public ResponseEntity<?> searchEmployee(@PathVariable String username) {
        return ResponseEntity.ok(this.userService.findByUsername(username));
    }

    @PostMapping("/hire-service")
    public ResponseEntity<?> hireService(@RequestBody ServiceDTO serviceDto) {
        boolean success = this.clientService.hireService(serviceDto);
        if(success){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployeeDetails(@PathVariable Long id) {
        return ResponseEntity.ok(this.clientService.getEmployeeDetailsById(id));
    }

    @GetMapping("/hirings/{clientId}")
    public ResponseEntity<?> getAllHiringsByUserId(@PathVariable Long clientId){
        return ResponseEntity.ok(clientService.getHiringsByUserId(clientId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@RequestBody ReviewDTO reviewDTO){
        boolean success = clientService.giveReview(reviewDTO);
        if(success){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
