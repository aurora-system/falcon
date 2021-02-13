package com.falcon.userprofile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDto {

    private long id;
    @NotBlank
    @Size(min = 3, max = 32, message = "Username must have 3 to 32 characters")
    private String username;
    @NotBlank
    @JsonIgnore
    private String password;
    @Email @NotBlank
    private String email;
    private String firstName;
    private String lastName;
    @NotBlank
    private String role;

    private String newPassword;
}
