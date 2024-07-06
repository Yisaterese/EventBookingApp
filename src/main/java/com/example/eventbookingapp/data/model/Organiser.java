package com.example.eventbookingapp.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Organiser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organiserId;
    @NotBlank(message="password is required")
    private String password;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format. Please provide a valid email address.")
    @NotNull
    @Column(unique = true)
    private String email;
    @OneToMany(fetch = FetchType.EAGER,cascade= CascadeType.ALL)
    private List<Event> events = new ArrayList<>();
}
