package com.example.eventbookingapp.service;

import com.example.eventbookingapp.data.model.Guest;
import com.example.eventbookingapp.data.repository.GuestRepository;
import com.example.eventbookingapp.dto.request.RegisterGuestRequest;
import com.example.eventbookingapp.dto.response.RegisterGuestResponse;
import com.example.eventbookingapp.exception.InvitedGuestException;
import com.example.eventbookingapp.exception.NoGuestFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GuestServiceImpl implements GuestService{
    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;

    public GuestServiceImpl(ModelMapper modelMapper, GuestRepository guestRepository) {
        this.modelMapper = modelMapper;
        this.guestRepository = guestRepository;
    }

    @Override
    public RegisterGuestResponse registerGuest(RegisterGuestRequest request){
        validateGuest(request);
        Guest newGuest = modelMapper.map(request, Guest.class);
        guestRepository.save(newGuest);
        RegisterGuestResponse response = modelMapper.map(newGuest, RegisterGuestResponse.class);
        response.setMessage("Registered successfully");
        return response;
    }

    @Override
    public Guest getGuest(Long id){
        return guestRepository.findById(id)
                .orElseThrow(()->new NoGuestFoundException("No guest found"));
    }

    @Override
    public Guest getGuestByEmail(String username) {
        return guestRepository.findByEmail(username);
    }

    private void validateGuest(RegisterGuestRequest request) {
        if(guestRepository.existsByEmail(request.getEmail()))throw new InvitedGuestException("Email already taken");
    }
}
