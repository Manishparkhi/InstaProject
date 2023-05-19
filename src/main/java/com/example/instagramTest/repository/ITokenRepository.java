package com.example.instagramTest.repository;

import com.example.instagramTest.model.AuthenticationToken;
import com.example.instagramTest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITokenRepository extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findByUser(User user);

    AuthenticationToken findFirstByToken(String token);
}
