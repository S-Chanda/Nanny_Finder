package com.project.nannyfinder.repository;

import com.project.nannyfinder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);

    User findByEmailIgnoreCase(String emailId);




}
