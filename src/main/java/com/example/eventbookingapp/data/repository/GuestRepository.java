package com.example.eventbookingapp.data.repository;

import com.example.eventbookingapp.data.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    @Query("SELECT COUNT(g) > 0 FROM Guest g WHERE g.email = :email")
    boolean existsByEmail(@Param("email") String email);

    Guest findByEmail(String username);
}
