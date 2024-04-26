package com.project.nannyfinder.repository;

import com.project.nannyfinder.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository <PasswordResetToken, Long> {
    PasswordResetToken findByToken(String theToken);
}
