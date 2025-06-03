package com.srbms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.srbms.util.CollectionUtil;
import com.srbms.dto.Booking;


public class BookingDaoImplementation implements BookingDao {

    private Map<String, List<Booking>> bookingRepo = CollectionUtil.bookingRepo;

    @Override
    public boolean addBooking(String userID, Booking booking) {
        List<Booking> bookings=new ArrayList<>();
        bookings.add(booking);
        if (!bookingRepo.containsKey(userID)) {
            bookingRepo.put(userID, bookings);
            return true;
        }
        return false;
    }

    @Override
    public List<Booking> viewBooking(String userID) {
        return bookingRepo.get(userID);
    }

    @Override
    public List<Booking> viewAllBookings() {
        return bookingRepo.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }


    @Override
    public boolean cancelBooking(String userID) {
        if (bookingRepo.containsKey(userID)) {
            bookingRepo.remove(userID);
            return true;
        }
        return false;
    }
}