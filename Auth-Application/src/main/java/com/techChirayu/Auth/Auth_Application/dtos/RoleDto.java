package com.techChirayu.Auth.Auth_Application.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoleDto {

    private UUID id=UUID.randomUUID();
    private String name;
}
