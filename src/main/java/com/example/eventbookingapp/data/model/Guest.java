package com.example.eventbookingapp.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity

public class Guest {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long guestId;
    private String userName;
    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email address")
    private String email;
    private int numberOfTickets;
    @Enumerated(EnumType.STRING)
    private ROLE roleAs = ROLE.ATTENDEE;
    @ManyToOne
    private Event event;
    @OneToMany(fetch = FetchType.EAGER,cascade= CascadeType.ALL,orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

}
