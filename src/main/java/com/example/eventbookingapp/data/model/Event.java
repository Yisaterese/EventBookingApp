package com.example.eventbookingapp.data.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
//@ToString
public class Event {
    @Id
    private Long eventId;
    private String title;
    private String eventDescription;
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime endTime;
    private String location;
    @OneToMany(fetch = FetchType.EAGER,cascade= CascadeType.ALL,orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<Ticket>();
    @OneToMany(fetch = FetchType.EAGER,cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Guest> guests = new ArrayList<>();
    @ManyToOne
    private Organiser organiser;
    @OneToMany(fetch = FetchType.EAGER,cascade= CascadeType.ALL,orphanRemoval = true)
    private List<Guest> attendees = new ArrayList<>();
}
