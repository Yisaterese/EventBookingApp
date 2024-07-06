package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.*;
import com.example.eventbookingapp.dto.request.*;
import com.example.eventbookingapp.dto.response.AddTicketResponse;
import com.example.eventbookingapp.dto.response.CreateEventResponse;
import com.example.eventbookingapp.dto.response.RegisterGuestResponse;
import com.example.eventbookingapp.dto.response.RegisterOrganiserResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.eventbookingapp.data.model.CATEGORY.VVIP;
import static com.example.eventbookingapp.data.model.TicketStatus.RESERVED;
import static java.util.Calendar.JULY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class EventServiceImplTest {
@Autowired
private OrganiserService organiserService;
@Autowired
private EventService eventService;
@Autowired
private GuestService guestservice;
@Autowired
TicketService ticketService;
    @Test
   public void getEventByOrganiser() {
         final RegisterOrganiserRequest request = buildRegisterOrganiserRequest();
        RegisterOrganiserResponse response = organiserService.registerOrganiser(request);
        assertNotNull(response);
        assertEquals("Registered successfully", response.getMessage());
        Organiser organiser = organiserService.getOrganiserById(response.getOrganiserId());
        final CreateEventRequest createEventRequest = buildCreateEventRequest(organiser);
        CreateEventResponse createEventResponse = organiserService.createEvent(createEventRequest);
        assertNotNull(createEventResponse);
        List<Event> events = eventService.getEventByOrganiser(organiser.getOrganiserId());
        assertEquals(1, events.size());
    }

     private static CreateEventRequest buildCreateEventRequest(Organiser organiser) {
          CreateEventRequest createEventRequest = new CreateEventRequest();
          createEventRequest.setTitle("Test Event");
          createEventRequest.setEventDescription("Test Event with description");
          createEventRequest.setLocation("Sabo yaba");
          createEventRequest.setStartTime(LocalDateTime.of(2024,JULY,03,04,30));
          createEventRequest.setEndTime(LocalDateTime.of(2024,JULY,03,06,30));
          createEventRequest.setOrganiserId(organiser.getOrganiserId());
          return createEventRequest;
     }

     private static RegisterOrganiserRequest buildRegisterOrganiserRequest() {
          RegisterOrganiserRequest request = new RegisterOrganiserRequest();
          request.setEmail("joseph@gmail.com");
          request.setPassword("#Password123");
          request.setUserName("terese");
          return request;
     }

     @Test
     public void reserveTicketTest(){
        //Register organiser
         RegisterOrganiserRequest request = buildRegisterOrganiserRequest();
         request.setEmail("terese@gmail.com");
         RegisterOrganiserResponse response = organiserService.registerOrganiser(request);
         assertNotNull(response);
         Organiser organiser = organiserService.getOrganiserById(response.getOrganiserId());
         //create event
         CreateEventRequest createEventRequest = buildCreateEventRequest(organiser);
         CreateEventResponse createEventResponse = organiserService.createEvent(createEventRequest);
         assertNotNull(createEventResponse.getMessage());
         //register guest
         RegisterGuestRequest createAttendeeRegisterRequest = buildCreateAttendeeRegisterRequest();
         RegisterGuestResponse registerAttendeeResponse = guestservice.registerGuest(createAttendeeRegisterRequest);
         assertNotNull(registerAttendeeResponse);
         //add ticket
         final AddTicketRequest addTicketRequest = buildAddTicketRequest(createEventResponse);
         AddTicketResponse addTicketResponse = ticketService.addTicket(addTicketRequest);
         assertNotNull(addTicketResponse);
         //make reservation
         MakeReservationRequest makeReservationRequest = new MakeReservationRequest();
         makeReservationRequest.setGuestId(registerAttendeeResponse.getGuestId());
         makeReservationRequest.setEventId(createEventResponse.getEventId());
         makeReservationRequest.setType(VVIP);
         eventService.makeReservation(makeReservationRequest);
         Ticket reservedTicket = ticketService.getTicketById(createEventResponse.getEventId(), addTicketResponse.getId());
         Assertions.assertThat(reservedTicket.getTicketStatus()).isEqualTo(RESERVED);

    }

    private static AddTicketRequest buildAddTicketRequest(CreateEventResponse createEventResponse) {
        AddTicketRequest addTicketRequest = new AddTicketRequest();
        addTicketRequest.setPurchaserMail("tereseyisajoseph@gmail.com");
        addTicketRequest.setEventId(createEventResponse.getEventId());
        addTicketRequest.setAmount(BigDecimal.valueOf(1000));
        addTicketRequest.setType(VVIP);
        addTicketRequest.setTicketStatus(RESERVED);
        return addTicketRequest;
    }

    private RegisterGuestRequest buildCreateAttendeeRegisterRequest() {
         RegisterGuestRequest request = new RegisterGuestRequest();
         request.setEmail("joe@gmail.com");
         request.setUserName("joe");
         return  request;
     }
}