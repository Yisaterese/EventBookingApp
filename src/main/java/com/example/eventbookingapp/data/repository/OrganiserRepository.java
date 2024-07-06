package com.example.eventbookingapp.data.repository;

import com.example.eventbookingapp.data.model.Organiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganiserRepository extends JpaRepository<Organiser,Long> {
    boolean existsByEmail(String email);
}
