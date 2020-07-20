package com.falcon.config.security.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.falcon.userprofile.UserRepository;

public class FalconUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    
    @Autowired
    public FalconUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username != null) {
            if (username.indexOf("@") != -1) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Email not found."));
            } else {
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
            }
        }
        return null;
    }

}
