package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.*;
import com.example.eventbookingapp.data.repository.TicketRepository;
import com.example.eventbookingapp.dto.request.*;
import com.example.eventbookingapp.dto.response.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.eventbookingapp.data.model.CATEGORY.VVIP;
import static com.example.eventbookingapp.data.model.TicketStatus.RESERVED;
import static com.example.eventbookingapp.data.model.TicketStatus.SOLD;
import static java.util.Calendar.JULY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class EventServiceImplTest {
@Autowired
private OrganiserService organiserService;
@Autowired
private EventService eventService;
@Autowired
private GuestService guestservice;
@Autowired
TicketService ticketService;

@Autowired
    TicketRepository ticketRepository;


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
     public void makeTicketReservationTest(){
         //Register organiser
         RegisterOrganiserRequest request = buildRegisterOrganiserRequest();
         request.setEmail("terese@gmail.com");
         RegisterOrganiserResponse response = organiserService.registerOrganiser(request);
         assertNotNull(response);
         Organiser organiser = organiserService.getOrganiserById(response.getOrganiserId());

         CreateEventRequest createEventRequest = buildCreateEventRequest(organiser);
         CreateEventResponse createEventResponse = organiserService.createEvent(createEventRequest);
         Event foundEvent = eventService.getEvent(createEventResponse.getEventId());
         assertNotNull(createEventResponse.getMessage());

         RegisterGuestRequest registerGuestRequest = buildRegisterGuestRequest(createEventResponse);
         RegisterGuestResponse registerAttendeeResponse = guestservice.registerGuest(registerGuestRequest);
         Guest foundGuest = guestservice.getGuest(registerAttendeeResponse.getGuestId());

         final AddTicketRequest addTicketRequest = buildAddTicketRequest(createEventResponse);
         AddTicketResponse addTicketResponse = ticketService.addTicket(addTicketRequest);
         assertNotNull(addTicketResponse);

         MakeReservationRequest makeReservationRequest = new MakeReservationRequest();
         makeReservationRequest.setGuestId(foundGuest.getGuestId());
         makeReservationRequest.setEventId(createEventResponse.getEventId());
         makeReservationRequest.setTicketType(VVIP);
         MakeReservationResponse makeReservationResponse = eventService.makeReservation(makeReservationRequest);
         Ticket reservedTicket = ticketService.getTicketById(addTicketResponse.getId());
         assertNotNull(makeReservationResponse);
         assertThat(reservedTicket.getTicketStatus()).isEqualTo(RESERVED);




     }

    private static AddTicketRequest buildAddTicketRequest(CreateEventResponse createEventResponse) {
        AddTicketRequest addTicketRequest = new AddTicketRequest();
        addTicketRequest.setEventId(createEventResponse.getEventId());
        addTicketRequest.setAmount(BigDecimal.valueOf(1000));
        addTicketRequest.setCategory(VVIP);
        return addTicketRequest;
    }

    private RegisterGuestRequest buildRegisterGuestRequest(CreateEventResponse createEventResponse) {
         RegisterGuestRequest request = new RegisterGuestRequest();
         request.setEmail("joe@gmail.com");
         request.setUserName("joe");
        try {
            request.setPassword("#Password123");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  request;
     }


     @Test
     @Sql(scripts = {"/db/data.sql"})
     public void purchaseTicketTest(){
     PurchaseTicketRequest purchaseTicketRequest = new PurchaseTicketRequest();
     purchaseTicketRequest.setEventId(200L);
     purchaseTicketRequest.setCategory(VVIP);
     purchaseTicketRequest.setAmount(BigDecimal.valueOf(25000.00));
     purchaseTicketRequest.setPurchaserMail("Johnson1@gmail.com");
     PurchaseTicketResponse purchaseTicketResponse = eventService.purchaseTicket(purchaseTicketRequest);
     assertThat(purchaseTicketResponse).isNotNull();
     Ticket purchasedTicket = ticketService.getTicketById(purchaseTicketResponse.getTicketId());
     assertThat(purchasedTicket.getTicketStatus()).isEqualTo(SOLD);
}
}