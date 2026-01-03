package com.techChirayu.Auth.Auth_Application.services.impl;

import com.techChirayu.Auth.Auth_Application.dtos.UserDto;
import com.techChirayu.Auth.Auth_Application.services.AuthService;
import com.techChirayu.Auth.Auth_Application.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto registorUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserDto userDto1 = userService.createUser(userDto);
        return userDto1;
    }
}
