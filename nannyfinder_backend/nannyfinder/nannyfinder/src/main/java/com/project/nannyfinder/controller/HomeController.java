package com.project.nannyfinder.controller;

import com.project.nannyfinder.dto.ServiceDTO;
import com.project.nannyfinder.model.User;
import com.project.nannyfinder.service.ClientService;
import com.project.nannyfinder.service.EmployeeService;
import com.project.nannyfinder.service.UserService;
import com.project.nannyfinder.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserService userService;



    @Autowired
    private EmployeeService employeeService;

    //path to call this method -> localhost:9090/home/users
    @GetMapping("/users")
    public List<User> getUser(){
        System.out.println("getting users");
        return userServiceImpl.getUsers();
    }

    @GetMapping("/current-user")
    //principal is a type of object which returns the currently logged-in user
    public String getLoggedInUser(Principal principal){
        return principal.getName();

    }



}
