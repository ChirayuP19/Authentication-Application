package com.techChirayu.Auth.Auth_Application.security;

import com.techChirayu.Auth.Auth_Application.entity.User;
import com.techChirayu.Auth.Auth_Application.exceptions.ResourceNotFoundException;
import com.techChirayu.Auth.Auth_Application.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class customUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("Invalid Email or Password!!"));
    }
}
