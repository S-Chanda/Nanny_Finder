package com.project.nannyfinder.service.implementation;

import com.project.nannyfinder.model.PasswordResetToken;
import com.project.nannyfinder.model.User;
import com.project.nannyfinder.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class PasswordResetTokenService {

    @Autowired
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        PasswordResetToken passwordRestToken = new PasswordResetToken(passwordToken, user);
        this.passwordResetTokenRepository.save(passwordRestToken);
    }

    public String validatePasswordResetToken(String theToken) {
        PasswordResetToken token = this.passwordResetTokenRepository.findByToken(theToken);
        if (token == null) {
            return "Invalid password reset token";
        } else {
            User user = token.getUser();
            Calendar calendar = Calendar.getInstance();
            return token.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0L ? "Link already expired, Resend link." : "valid";
        }
    }

    public Optional<User> findUserByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(this.passwordResetTokenRepository.findByToken(passwordResetToken).getUser());
    }

    public PasswordResetToken findPasswordResetToken(String token) {
        return this.passwordResetTokenRepository.findByToken(token);
    }

    public PasswordResetTokenService(final PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }
}
