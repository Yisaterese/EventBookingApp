package com.example.eventbookingapp.dto.utility;

import java.security.SecureRandom;
public class TicketNumber {
    private  final SecureRandom random = new SecureRandom();

    public String generateTicketNumber(){
        return String.valueOf(random.nextLong(10000,1000000));
    }

}
