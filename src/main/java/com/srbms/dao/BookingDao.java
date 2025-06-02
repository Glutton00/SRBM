package com.srbms.dao;

import com.srbms.dto.Booking;
import java.util.List;

public interface BookingDao {
    boolean addBooking(String userID, Booking booking);
    List<Booking> viewBooking(String userID);
    List<Booking> viewAllBookings();
    boolean cancelBooking(String userID);
}