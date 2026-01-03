package com.techChirayu.Auth.Auth_Application.dtos;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.Date;


public record ErrorResponse(
        String message,
        HttpStatus status

) {

}
