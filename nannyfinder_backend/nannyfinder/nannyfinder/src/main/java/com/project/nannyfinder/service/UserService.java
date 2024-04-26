package com.project.nannyfinder.service;

import com.project.nannyfinder.model.User;
import com.project.nannyfinder.model.UserRole;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface UserService {
    //creating user
    public User createUser(User user, Set<UserRole> userRoles);

    //get user by user name
    public User getUser(String username);

    //delete user by id
    public void deleteUser(Long id);

    //update user
    public User updateUser(User user);

    //list users
    public List<User> getUsers();

    //get user by id
    public User getUserById(Long id);



    ///for email
    ResponseEntity<?> confirmEmail(String confirmationToken);

    User findByUsername(String username);

    void createPasswordResetTokenForUser(User user, String passwordToken);

    String validatePasswordResetToken(String passwordResetToken);

    User findUserByPasswordToken(String passwordResetToken);

    void changePassword(User user, String newPassword);

    boolean verifyOldPassword(User user, String oldPassword);


}
