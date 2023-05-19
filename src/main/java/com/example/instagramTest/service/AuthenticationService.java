package com.example.instagramTest.service;

import com.example.instagramTest.model.AuthenticationToken;
import com.example.instagramTest.model.User;
import com.example.instagramTest.repository.ITokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    ITokenRepository iTokenRepo;

    public void saveToken(AuthenticationToken token){
        iTokenRepo.save(token);
    }

    public AuthenticationToken getToken(User user){
        return iTokenRepo.findByUser(user);
    }

    public boolean authenticate(String email,String token){
        AuthenticationToken authenticationToken = iTokenRepo.findFirstByToken(token);
        Optional<String> expectedMail = Optional.ofNullable(authenticationToken.getUser().getName());
        if(expectedMail.isPresent()){
            return true;
        }else
            return false;
    }
}
