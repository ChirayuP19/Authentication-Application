package com.techChirayu.Auth.Auth_Application.controllers;

import com.techChirayu.Auth.Auth_Application.dtos.UserDto;
import com.techChirayu.Auth.Auth_Application.services.UserService;
import com.techChirayu.Auth.Auth_Application.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
     return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @GetMapping
    public ResponseEntity<Iterable<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
