package com.example.eventbookingapp.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AttendeeRegisterRequest {
    private String email;
    private String username;

}
