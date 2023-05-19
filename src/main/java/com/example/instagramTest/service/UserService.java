package com.example.instagramTest.service;

import com.example.instagramTest.dto.SignInInput;
import com.example.instagramTest.dto.SignInOutput;
import com.example.instagramTest.dto.SignUpInput;
import com.example.instagramTest.dto.SignUpOutput;
import com.example.instagramTest.model.AuthenticationToken;
import com.example.instagramTest.model.User;
import com.example.instagramTest.repository.IUserRepository;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;


    public SignUpOutput signUp(SignUpInput signUpInput) {
        User user1 = userRepository.findFirstByEmail(signUpInput.getEmail());

        if(user1 != null){
            throw new IllegalStateException("Patient already exists by this details..");
        }

        //encryption
        String encryptedPassword = null;
        try{
            encryptedPassword = encryptPassword(signUpInput.getUserPassword());
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        //save the user
        User user = new User(signUpInput.getFirstName(),signUpInput.getLastName(),signUpInput.getAge(),signUpInput.getEmail(),encryptedPassword,signUpInput.getPhoneNumber());

        userRepository.save(user);

        AuthenticationToken token = new AuthenticationToken();
        authenticationService.saveToken(token);
        return new SignUpOutput(HttpStatus.ACCEPTED,"User registered Successfully");
    }

    private String encryptPassword(String userPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(userPassword.getBytes());
        byte[] digested =  md5.digest();

        String hash = DatatypeConverter.printHexBinary(digested);
        return hash;
    }

    public SignInOutput signIn(SignInInput signInInput){
        User user = userRepository.findFirstByEmail(signInInput.getEmail());
        if(user == null){
            throw new IllegalStateException("User invalid..!!!");
        }
        //encrypt the password
        String encryptedPassword = null;
        try{
            encryptedPassword = encryptPassword(signInInput.getPassword());
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        //match it with database encryption
        boolean isPasswordValid = encryptedPassword.equals(user.getPassword());
        if(!isPasswordValid){
            throw new IllegalStateException("User invalid..!!!!!!!!");
        }
        AuthenticationToken token = authenticationService.getToken(user);
        return new SignInOutput(HttpStatus.OK,token.getToken());

    }

    public void updateUser(SignUpInput signUpInput) {
        User user1 = userRepository.findFirstByEmail(signUpInput.getEmail());
        if(user1 == null){
            throw new IllegalStateException("User invalid..!!!");
        }
        String encryptedPassword = null;
        if(signUpInput.getEmail() != null)
        {
            try {
                encryptedPassword = encryptPassword(signUpInput.getUserPassword());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        //save the user
        User user = new User(user1.getUserID(),signUpInput.getFirstName(),signUpInput.getLastName(),signUpInput.getAge(),signUpInput.getEmail(),encryptedPassword,signUpInput.getPhoneNumber());

        userRepository.save(user);
    }
}
