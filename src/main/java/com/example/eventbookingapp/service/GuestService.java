package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.Guest;
import com.example.eventbookingapp.dto.request.RegisterGuestRequest;
import com.example.eventbookingapp.dto.response.RegisterGuestResponse;


public interface GuestService {
    RegisterGuestResponse registerGuest(RegisterGuestRequest request);

    Guest getGuest(Long id);

    Guest getGuestByEmail(String username);
}
