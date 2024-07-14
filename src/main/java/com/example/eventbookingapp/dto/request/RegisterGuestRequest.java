package com.example.eventbookingapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterGuestRequest {
    private String email;
    private String password;
    private String userName;
}
