package com.srbms.service;

import com.srbms.dao.BookingDao;
import com.srbms.dao.BookingDaoImplementation;
import com.srbms.dto.Booking;
import com.srbms.dto.Resource;
import com.srbms.util.CollectionUtil;
import com.srbms.customException.BookingFailedException;
import com.srbms.customException.NoBookingFoundException;
import com.srbms.customException.ResourceNotFoundException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BookingServiceImplementation implements BookingService {

    private List<Booking> bookings;
    private Booking booking;
    private BookingDao bookingDao;
    private final Map<String, List<Booking>> bookingRepo = CollectionUtil.bookingRepo;

    public BookingServiceImplementation(String userId) {
        if (bookingRepo.containsKey(userId)) {
            this.bookings = bookingRepo.get(userId);
        }
        this.bookingDao=new BookingDaoImplementation();
    }



    @Override
    public boolean bookResources(String userId, List<Resource> resources, LocalDate startDate, LocalDate endDate) throws BookingFailedException {
        if (resources == null || resources.isEmpty()) {
            throw new BookingFailedException("No resources selected for booking.");
        }

        if (endDate.isBefore(startDate)) {
            throw new BookingFailedException("End date cannot be before start date.");
        }

        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        String bookingId = UUID.randomUUID().toString();
        ResourceService resourceService = new ResourceServiceImplementation();

        List<Resource> finalResources = new ArrayList<>();
        for (Resource resource : resources) {
            if (resource.isResourceIsAvailable()) {
                finalResources.add(resource);
                resource.setResourceIsAvailable(false);
                try {
					resourceService.updateResource(resource);
				} catch (ResourceNotFoundException e) {
				    System.out.println("Error: " + e.getMessage());
				}
            }
        }

        if (finalResources.isEmpty()) {
            throw new BookingFailedException("All selected resources are unavailable for booking.");
        }

        double dailyCost = finalResources.stream()
                .mapToDouble(Resource::getResourceCost)
                .sum();
        double totalCost = numberOfDays * dailyCost;

        booking = new Booking(bookingId, startDate, endDate, finalResources, totalCost);
        booking.setBookingResources(new ArrayList<>(finalResources));

        List<Booking> userBookings = bookingDao.viewBooking(userId);
        if (userBookings == null) {
            userBookings = new ArrayList<>();
        }
        userBookings.add(booking);
        bookingRepo.put(userId, userBookings);

        System.out.println("Booking successful for user: " + userId);
        System.out.println("Booking Duration: " + numberOfDays + " days");
        System.out.println("Total Booking Cost: " + totalCost);

        return true;
    }



    @Override
    public List<Booking> seeBookings(String userId) {
        bookings=bookingRepo.get(userId);
        if(bookings==null)
        {
            return new ArrayList<>();
        }
       return bookings;
    }

    @Override
    public boolean cancelBooking(String userId, String bookingId) throws NoBookingFoundException {
        List<Booking> userBookings = bookingRepo.get(userId);

        if (userBookings != null && !userBookings.isEmpty()) {
            Iterator<Booking> iterator = userBookings.iterator();
            while (iterator.hasNext()) {
                Booking booking = iterator.next();
                if (booking.getBookingID().equals(bookingId)) {
                    iterator.remove();
                    if (userBookings.isEmpty()) {
                        bookingRepo.remove(userId);
                    }
                    System.out.println("Booking cancelled for user: " + userId);
                    return true;
                }
            }
        }

        throw new NoBookingFoundException("No matching booking found for user ID: " + userId + " and booking ID: " + bookingId);
    }

    @Override
    public List<Booking> viewAllBookings() {
        return bookingDao.viewAllBookings();
    }

}

