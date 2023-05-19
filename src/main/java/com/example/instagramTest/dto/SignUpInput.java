package com.example.instagramTest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpInput {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    private Integer age;
    @Email
    private String email;

    private String userPassword;
    @Length(max = 12, min = 10)
    private String phoneNumber;
}
