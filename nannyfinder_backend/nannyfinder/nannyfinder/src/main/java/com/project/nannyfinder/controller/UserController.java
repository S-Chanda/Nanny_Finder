package com.project.nannyfinder.controller;

import com.project.nannyfinder.model.*;
import com.project.nannyfinder.service.UserService;
import com.project.nannyfinder.service.implementation.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//for endpoints
@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;



    //creating user
    @PostMapping("/create-user")
    public User createUser(@RequestBody User user) {
        //encoding password with BCryptPasswordEncoder
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        Set<UserRole> roles = new HashSet<>();

        Role role = new Role();
        role.setRoleId(46L);
        role.setRoleName("NORMAL");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        roles.add(userRole);

        return this.userService.createUser(user, roles);
    }

    @GetMapping("/{username}")
    //getting user
    public User getUser(@PathVariable("username") String username){
        return this.userService.getUser(username);
    }

    @GetMapping("single/{id}")
    //getting user
    public User getUserById(@PathVariable("id") Long id){
        return this.userService.getUserById(id);
    }

    //delete user by id
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        this.userService.deleteUser(id);

    }

//    update user profile
    @PatchMapping("/update")
    public User updateProfile(@RequestBody User user){
        return this.userService.updateUser(user);
    }





    @PostMapping("/create-employee")
    public User createEmployee(@RequestBody User user) {

        //encoding password with BCryptPasswordEncoder
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        Set<UserRole> roles = new HashSet<>();

        Role role = new Role();
        role.setRoleId(45L);
        role.setRoleName("EMPLOYEE");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        roles.add(userRole);

        return this.userService.createUser(user, roles);

    }

    @GetMapping("/users")
    public List<User> getAllUser(){
        System.out.println("getting users");
        return this.userService.getUsers();
    }



    @RequestMapping(value="/confirm-account", method={RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken){
        return userService.confirmEmail(confirmationToken);
    }

    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(@RequestBody PasswordResetUtil passwordResetUtil, final HttpServletRequest request) {
        User user = this.userService.findByUsername(passwordResetUtil.getUsername());
        String passwordResetUrl = "";
        if (user.isEnabled()) {
            String passwordResetToken = UUID.randomUUID().toString();
            this.userService.createPasswordResetTokenForUser(user, passwordResetToken);
            passwordResetUrl = this.passwordResetEmailLink(user, this.applicationUrl(request), passwordResetToken);
        }

        return passwordResetUrl;
    }

    private String passwordResetEmailLink(User user, String applicationUrl, String passResetToken) {
        String url = applicationUrl + "/user/reset-password?token=" + passResetToken;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Reset your Password!");
        mailMessage.setText("To reset your account, please click here : http://localhost:9090/user/reset-password?token=" + passResetToken);
        this.emailService.sendEmail(mailMessage);
        System.out.println("Click on link to reset your password" + url);
        return url;
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetUtil passwordResetUtil, @RequestParam("token") String passwordResetToken) {
        String tokenValidationResult = this.userService.validatePasswordResetToken(passwordResetToken);
        if (!tokenValidationResult.equalsIgnoreCase("valid")) {
            return "Invalid password request token";
        } else {
            User user = this.userService.findUserByPasswordToken(passwordResetToken);
            if (user != null) {
                this.userService.changePassword(user, passwordResetUtil.getNewPassword());
                return "Password has been reset successfully";
            } else {
                return "Invalid password reset token";
            }
        }
    }

    public String applicationUrl(HttpServletRequest request) {
        String var10000 = request.getServerName();
        return "http://" + var10000 + ":" + request.getServerPort() + request.getContextPath();
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestBody PasswordResetUtil resetUtil) {
        User user = this.userService.findByUsername(resetUtil.getUsername());
        if (!this.userService.verifyOldPassword(user, resetUtil.getOldPassword())) {
            return "Incorrect old password";
        } else {
            this.userService.changePassword(user, resetUtil.getNewPassword());
            return "Password changed successfully !";
        }
    }


}

