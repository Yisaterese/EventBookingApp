package com.example.eventbookingapp.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

import static com.example.eventbookingapp.data.model.TicketStatus.AVAILABLE;

@Setter
@Getter
@Entity
@ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private CATEGORY category       ;
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus = TicketStatus.AVAILABLE;
    private BigDecimal discount;
    private String ticketNumber;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format. Please provide a valid email address.")
    private String purchaserMail;
    @ManyToOne
    private Event event;
    @ManyToOne
    private Guest guest;
}




