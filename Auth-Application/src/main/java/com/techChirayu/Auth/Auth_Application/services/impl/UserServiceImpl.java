package com.techChirayu.Auth.Auth_Application.services.impl;

import com.techChirayu.Auth.Auth_Application.dtos.UserDto;
import com.techChirayu.Auth.Auth_Application.entity.Provider;
import com.techChirayu.Auth.Auth_Application.entity.User;
import com.techChirayu.Auth.Auth_Application.exceptions.ResourceNotFoundException;
import com.techChirayu.Auth.Auth_Application.helpers.UserHelper;
import com.techChirayu.Auth.Auth_Application.repositories.UserRepository;
import com.techChirayu.Auth.Auth_Application.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if(userDto.getEmail()==null || userDto.getEmail().isBlank()){
            throw  new IllegalArgumentException("Email is requried");
        }
        if(userRepository.existsByEmail(userDto.getEmail())){
            throw  new IllegalArgumentException("Email already exists");
        }
        User user = modelMapper.map(userDto, User.class);
        user.setProvider(userDto.getProvider()!=null ? userDto.getProvider(): Provider.LOCAL);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given Email ID!!"));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        UUID uuid = UserHelper.parseUUID(userId);
        User existingUser=userRepository.findById(uuid).orElseThrow(()->new ResourceNotFoundException("User not found with given id"));
        if(userDto.getName()!=null)
            existingUser.setName(userDto.getName());
        if(userDto.getImage()!=null)
            existingUser.setImage(userDto.getImage());
        if(userDto.getProvider()!=null)
            existingUser.setProvider(userDto.getProvider());
        existingUser.setEnable(userDto.isEnable());
        if(userDto.getPassword()!=null)
        existingUser.setPassword(userDto.getPassword());
        User save = userRepository.save(existingUser);
        return modelMapper.map(save,UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        UUID uId = UserHelper.parseUUID(userId);
        User user = userRepository.findById(uId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given ID"));
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserById(String userId) {

        User user = userRepository.findById(UserHelper.parseUUID(userId)).orElseThrow(() -> new ResourceNotFoundException("User not found with given ID"));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override

    public Iterable<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user,UserDto.class))
                .toList();
    }
}
