package com.example.eventbookingapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

@Setter
@Getter
public class RegisterOrganiserRequest {
    private String password;
    private String email;
}
