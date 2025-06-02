package com.srbms.service;

import com.srbms.dto.Booking;
import com.srbms.dto.Resource;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    boolean bookResources(String userId, List<Resource> resources, LocalDate startDate, LocalDate endDate);

    List<Booking> seeBookings(String userId);

    boolean cancelBooking(String userId, String bookingId);

    List<Booking> viewAllBookings();
}

