package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.Organiser;
import com.example.eventbookingapp.dto.request.RegisterOrganiserRequest;
import com.example.eventbookingapp.dto.request.CreateEventRequest;
import com.example.eventbookingapp.dto.request.CreateGuestRequest;
import com.example.eventbookingapp.dto.response.CreateEventResponse;
import com.example.eventbookingapp.dto.response.CreateGuestResponse;
import com.example.eventbookingapp.dto.response.RegisterOrganiserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static java.util.Calendar.JULY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class OrganiserServiceImplTest {
    @Autowired
    private OrganiserServiceImpl organiserService;

    @Test
  public void registerOrganiser() {
        RegisterOrganiserRequest request = new RegisterOrganiserRequest();
        request.setEmail("teresejoseph@gmail.com");
        request.setPassword("#Password123");
        RegisterOrganiserResponse response = organiserService.registerOrganiser(request);
        assertNotNull(response);
        assertEquals("Registered successfully", response.getMessage());

    }

    @Test
    public void viewAllEventsByOrganiserTest() {
        final RegisterOrganiserRequest request = buildRegisterOrganiserRequest();
        RegisterOrganiserResponse response = organiserService.registerOrganiser(request);
        assertNotNull(response);
        assertEquals("Registered successfully", response.getMessage());
        Organiser organiser = organiserService.getOrganiserById(response.getOrganiserId());
        CreateEventRequest createEventRequest = new CreateEventRequest();
        createEventRequest.setTitle("Test Event");
        createEventRequest.setEventDescription("Test Event with description");
        createEventRequest.setLocation("Sabo yaba");
        createEventRequest.setStartTime(LocalDateTime.of(2024,JULY,03,04,30));
        createEventRequest.setEndTime(LocalDateTime.of(2024,JULY,03,06,30));
        createEventRequest.setOrganiserId(organiser.getOrganiserId());
        CreateEventResponse createEventResponse = organiserService.createEvent(createEventRequest);
        assertThat(createEventResponse).isNotNull();
        assertThat(createEventResponse.getMessage()).containsIgnoringCase("event created successfully");

    }

    private static RegisterOrganiserRequest buildRegisterOrganiserRequest() {
        RegisterOrganiserRequest request = new RegisterOrganiserRequest();
        request.setEmail("terese@gmail.com");
        request.setPassword("#Password123");
        return request;
    }

    @Test
    public void addGuestTest(){
        final RegisterOrganiserRequest request = buildRegisterOrganiserRequest();
        request.setEmail("terese@Gmail.com");
        RegisterOrganiserResponse response = organiserService.registerOrganiser(request);
        CreateEventRequest createEventRequest = new CreateEventRequest();
        createEventRequest.setTitle("Test Event");
        createEventRequest.setEventDescription("Test Event with description");
        createEventRequest.setLocation("Sabo yaba");
        createEventRequest.setStartTime(LocalDateTime.of(2024,JULY,03,04,30));
        createEventRequest.setEndTime(LocalDateTime.of(2024,JULY,03,06,30));
        Organiser organiser = organiserService.getOrganiserById(response.getOrganiserId());

        createEventRequest.setOrganiserId(organiser.getOrganiserId());
        CreateEventResponse createEventResponse = organiserService.createEvent(createEventRequest);
        assertThat(createEventResponse).isNotNull();
        assertThat(createEventResponse.getMessage()).containsIgnoringCase("event created successfully");

        CreateGuestRequest createGuestRequest  = new CreateGuestRequest();
        createGuestRequest.setEmail("guest@example.com");
        createGuestRequest.setEventId(createEventResponse.getEventId());
        createGuestRequest.setUserName("Rapizo");
        CreateGuestResponse createGuestResponse = organiserService.addGuest(createGuestRequest);
        assertThat(createGuestResponse).isNotNull();
        assertThat(createGuestResponse.getMessage()).containsIgnoringCase("guest added successfully");
    }


}