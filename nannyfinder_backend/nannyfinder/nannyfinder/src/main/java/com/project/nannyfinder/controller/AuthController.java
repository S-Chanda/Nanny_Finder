package com.project.nannyfinder.controller;

import com.project.nannyfinder.model.JwtRequest;
import com.project.nannyfinder.model.JwtResponse;
import com.project.nannyfinder.model.User;
import com.project.nannyfinder.security.JwtUtils;
import com.project.nannyfinder.service.implementation.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Random;


@RestController
@CrossOrigin("*")
public class AuthController {
    Random random = new Random(1000);

    @Autowired
    private UserDetailsService userDetailsService; //to extract user information


    @Autowired
    private AuthenticationManager manager; //to authenticate user


    @Autowired
    private JwtUtils jwtUtils; //to create Jwt

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private EmailService emailService;

    @PostMapping("/generate-token")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest request){
        try {
            this.doAuthenticate(request.getUsername(), request.getPassword());
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User not found !!");
        }
        //authenticate
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    private void doAuthenticate(String username, String password){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authentication);
        }
        catch (DisabledException e) {
            throw new DisabledException("User Disabled !!" + e.getMessage());
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password !!" + e.getMessage());
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler(){
        return "Credentials Invalid !!";
    }

    //returns the detail of current-user
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){
        return ((User)this.userDetailsService.loadUserByUsername(principal.getName()));
    }

    @PostMapping("/sendOTP")
    public int sendOTP(String email){
        System.out.println("Email: " + email);

        //generating otp of 4 digit
        int otp = random.nextInt(999999);
        System.out.println("OTP: " + otp);


        return otp;


    }


}
