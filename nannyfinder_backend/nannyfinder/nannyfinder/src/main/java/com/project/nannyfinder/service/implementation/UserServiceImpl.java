package com.project.nannyfinder.service.implementation;

import com.project.nannyfinder.model.ConfirmationToken;
import com.project.nannyfinder.model.User;
import com.project.nannyfinder.model.UserRole;
import com.project.nannyfinder.repository.ConfirmationTokenRepository;
import com.project.nannyfinder.repository.RoleRepository;
import com.project.nannyfinder.repository.UserRepository;
import com.project.nannyfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @Autowired
    PasswordEncoder passwordEncoder;

    //method to create new user
    @Override
    public User createUser(User user, Set<UserRole> userRoles)  {

        User local = this.userRepository.findByUsername(user.getUsername());
        if(local!=null){
            System.out.println("User already found!!");
            try {
                throw new Exception("User already present !!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else{
            //user create
            for(UserRole ur:userRoles){
                roleRepository.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);
            local = this.userRepository.save(user);

            // Save confirmation token
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);

            // Send email for account confirmation
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("To activate your NannyFinder account, please click here : "
                    + "http://localhost:9090/user/confirm-account?token=" + confirmationToken.getConfirmationToken());
            emailService.sendEmail(mailMessage);

            System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());

        }
        return local;

    }

    //getting user by username
    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(Long id) {

        this.userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    public List<User> getUsers(){

        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).get();
    }


    //////////////////////////////
    //For TOKEN





    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);



        if(token!=null){
            User user = userRepository.findByUsername(token.getUser().getUsername());
            user.setEnabled(true);
            userRepository.save(user);
            System.out.println("Email success");
            return ResponseEntity.ok("Your Nanny Finder account has been actived Successfully !");
        }
        else{
            System.out.println("Email not success");
            return ResponseEntity.badRequest().body("Error: Couldn't verify email.");

        }

    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        this.passwordResetTokenService.createPasswordResetTokenForUser(user, passwordToken);
    }

    public String validatePasswordResetToken(String passwordResetToken) {
        return this.passwordResetTokenService.validatePasswordResetToken(passwordResetToken);
    }

    public User findUserByPasswordToken(String passwordResetToken) {
        return (User)this.passwordResetTokenService.findUserByPasswordToken(passwordResetToken).get();
    }

    public void changePassword(User user, String newPassword) {
        user.setPassword(this.passwordEncoder.encode(newPassword));
        this.userRepository.save(user);
    }

    public boolean verifyOldPassword(User user, String oldPassword) {
        return this.passwordEncoder.matches(oldPassword, user.getPassword());
    }






}
