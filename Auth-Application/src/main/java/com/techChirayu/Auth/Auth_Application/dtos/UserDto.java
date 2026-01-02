package com.techChirayu.Auth.Auth_Application.dtos;

import com.techChirayu.Auth.Auth_Application.entity.Provider;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private UUID id;
    private String email;
    private String name;
    private String password;
    private String image;
    private boolean enable=true;
    private Instant createAt=Instant.now();
    private Instant updateAt=Instant.now();
    private Provider provider=Provider.LOCAL;
    private Set<RoleDto> roles=new HashSet<>();
}
