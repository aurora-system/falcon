package com.falcon.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.falcon.util.validator.PasswordMatches;
import com.falcon.util.validator.ValidEmail;

@PasswordMatches
public class UserDto {
    @NotNull
    @NotEmpty
    private String firstName;
     
    @NotNull
    @NotEmpty
    private String lastName;
     
    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    // standard getters and setters
    
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDto [firstName=").append(firstName).append(", lastName=").append(lastName)
				.append(", password=").append(password).append(", matchingPassword=").append(matchingPassword)
				.append(", email=").append(email).append("]");
		return builder.toString();
	}
     
}