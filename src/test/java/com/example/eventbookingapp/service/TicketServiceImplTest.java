package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.Organiser;
import com.example.eventbookingapp.dto.request.RegisterOrganiserRequest;
import com.example.eventbookingapp.dto.request.AddTicketRequest;
import com.example.eventbookingapp.dto.request.CreateEventRequest;
import com.example.eventbookingapp.dto.response.AddTicketResponse;
import com.example.eventbookingapp.dto.response.CreateEventResponse;
import com.example.eventbookingapp.dto.response.RegisterOrganiserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.example.eventbookingapp.data.model.CATEGORY.VVIP;
import static java.util.Calendar.JULY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TicketServiceImplTest {
    @Autowired
    private  TicketService ticketService;
    @Autowired
    private OrganiserService organiserService;

    @Test
    public void purchaseTicket(){

    }

    @Test
    public void AddTicketTest(){
        RegisterOrganiserRequest request = new RegisterOrganiserRequest();
        request.setEmail("joseph@gmail.com");
        request.setPassword("#Password123");
        request.setUserName("terese");
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
        assertNotNull(createEventResponse);
        AddTicketRequest addTicketRequest = new AddTicketRequest();
        addTicketRequest.setType(VVIP);
        addTicketRequest.setPurchaserMail("purchaser@gmail.com");
        addTicketRequest.setAmount(BigDecimal.valueOf(3000));
        addTicketRequest.setEventId(createEventResponse.getEventId());
       AddTicketResponse addTicketResponse = ticketService.addTicket(addTicketRequest);
       assertThat(addTicketResponse).isNotNull();
       assertThat(addTicketResponse.getMessage()).contains("success");


    }
}