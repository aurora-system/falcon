package com.falcon.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.falcon.entity.User;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

	/* User save(UserRegistrationDto registration); */
}