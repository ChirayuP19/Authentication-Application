package com.techChirayu.Auth.Auth_Application.controllers;

import com.techChirayu.Auth.Auth_Application.dtos.UserDto;
import com.techChirayu.Auth.Auth_Application.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registor")
    public ResponseEntity<UserDto> registor(@RequestBody UserDto userDto){
        return  ResponseEntity.status(HttpStatus.CREATED).body(authService.registorUser(userDto));
    }

}
