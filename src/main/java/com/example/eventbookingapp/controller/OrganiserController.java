package com.example.eventbookingapp.controller;

import com.example.eventbookingapp.dto.request.RegisterOrganiserRequest;
import com.example.eventbookingapp.dto.response.RegisterOrganiserResponse;
import com.example.eventbookingapp.dto.utility.ErrorMessageResponse;
import com.example.eventbookingapp.exception.EventBookingException;
import com.example.eventbookingapp.service.OrganiserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/organiser")
@RestController
@RequiredArgsConstructor
public class OrganiserController {
    private final OrganiserService organiserService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterOrganiserRequest registerOrganiserRequest) {
        try {
            RegisterOrganiserResponse response = organiserService.registerOrganiser(registerOrganiserRequest);
            return ResponseEntity.ok(response);
        } catch (EventBookingException e) {
            return ResponseEntity.badRequest().body(new ErrorMessageResponse(e.getMessage()));
        }
    }
}
