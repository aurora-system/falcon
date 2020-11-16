package com.falcon.config.security.simple;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.falcon.userprofile.UserRepository;

@Service
public class FalconUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    
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
        throw new UsernameNotFoundException("Username not found.");
    }

}
