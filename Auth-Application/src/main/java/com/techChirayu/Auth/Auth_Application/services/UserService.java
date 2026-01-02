package com.techChirayu.Auth.Auth_Application.services;

import com.techChirayu.Auth.Auth_Application.dtos.UserDto;

public interface UserService {

    // this is for the Create User.
   UserDto createUser(UserDto userDto);
    // this is for get userById;
    UserDto getUserByEmail(String email);
    // this is for the update User.
    UserDto updateUser(UserDto userDto,String userId);
    // this is for the delete.
    void deleteUser(String userId);
    UserDto getUserById(String userId);
    Iterable<UserDto>getAllUsers();

}
