package com.falcon;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FalconApplication {

	public static void main(String[] args) {
		SpringApplication.run(FalconApplication.class, args);
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
        int strength = 11;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(strength); 
        return encoder;
    }
    
}
