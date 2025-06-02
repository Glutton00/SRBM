package com.srbms.service;

import com.srbms.dao.BookingDao;
import com.srbms.dao.BookingDaoImplementation;
import com.srbms.dto.Booking;
import com.srbms.dto.Resource;
import com.srbms.util.CollectionUtil;

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
    public boolean bookResources(String userId, List<Resource> resources, LocalDate startDate, LocalDate endDate) {
        if (resources == null || resources.isEmpty()) {
            System.out.println("No resources selected for booking.");
            return false;
        }


        if (endDate.isBefore(startDate)) {
            System.out.println("End date cannot be before start date.");
            return false;
        }

        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1; // Include the end day



        String bookingId = UUID.randomUUID().toString();
        ResourceService resourceService=new ResourceServiceImplementation();
        List<Resource> finalResources=new ArrayList<>();
        for (Resource resource : resources) {
            if(resource.isResourceIsAvailable()) {
                finalResources.add(resource);
                resource.setResourceIsAvailable(false); // Mark as not available
                resourceService.updateResource(resource); // Update via service layer
            }else {

            }
        }
        if(finalResources.isEmpty())
        {
            System.out.println("resource can't be booked unavailable.");
            return false;
        }
        double dailyCost = finalResources.stream()
                .mapToDouble(Resource::getResourceCost)
                .sum();

        double totalCost = numberOfDays * dailyCost;
        booking = new Booking(bookingId, startDate, endDate, finalResources, totalCost);
        booking.setBookingResources(new ArrayList<>(finalResources));
        List<Booking> bookings;
        BookingDao bookingdao=new BookingDaoImplementation();
        bookings= bookingdao.viewBooking(userId);
        if(bookings==null)
        {
            bookings=new ArrayList<>();
        }
        bookings.add(booking);


        bookingRepo.put(userId, bookings);
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
    public boolean cancelBooking(String userId, String bookingId) {
        List<Booking> userBookings = bookingRepo.get(userId);

        if (userBookings != null && !userBookings.isEmpty()) {
            Iterator<Booking> iterator = userBookings.iterator();

            while (iterator.hasNext()) {
                Booking booking = iterator.next();
                if (booking.getBookingID().equals(bookingId)) {
                    iterator.remove(); // Remove the matching booking

                    // If the user's booking list is now empty, remove the user from the map
                    if (userBookings.isEmpty()) {
                        bookingRepo.remove(userId);
                    }

                    System.out.println("Booking cancelled for user: " + userId);
                    return true;
                }
            }
        }

        System.out.println("No matching booking found for user or booking ID.");
        return false;
    }

    @Override
    public List<Booking> viewAllBookings() {
        return bookingDao.viewAllBookings();
    }

}

