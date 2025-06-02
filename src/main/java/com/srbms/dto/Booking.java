package com.srbms.dto;
import java.time.LocalDate;

import java.time.LocalDate;
import java.util.List;

public class Booking {
    private String bookingID;
    private LocalDate bookingStartDate;
    private LocalDate bookingEndDate;
    private List<Resource> bookingResources;
    private double totalCost;

    // Constructor
    public Booking(String bookingID, LocalDate bookingStartDate, LocalDate bookingEndDate,
                   List<Resource> bookingResources, double totalCost) {
        this.bookingID = bookingID;
        this.bookingStartDate = bookingStartDate;
        this.bookingEndDate = bookingEndDate;
        this.bookingResources = bookingResources;
        this.totalCost = totalCost;
    }

    // Getters and Setters
    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public LocalDate getBookingStartDate() {
        return bookingStartDate;
    }

    public void setBookingStartDate(LocalDate bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public LocalDate getBookingEndDate() {
        return bookingEndDate;
    }

    public void setBookingEndDate(LocalDate bookingEndDate) {
        this.bookingEndDate = bookingEndDate;
    }

    public List<Resource> getBookingResources() {
        return bookingResources;
    }

    public void setBookingResources(List<Resource> bookingResources) {
        this.bookingResources = bookingResources;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        String allNames = bookingResources.stream()
                .map(Resource::getResourceName)
                .reduce("", (a, b) -> a + b);

        return
                "bookingID='" + bookingID +
                ",  bookingStartDate=" + bookingStartDate +
                ",  bookingEndDate=" + bookingEndDate +
                        " booking resources: "+allNames+
                ",  totalCost=" + totalCost;
    }
}

