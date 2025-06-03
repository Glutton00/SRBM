package com.srbms.servicetest;

import com.srbms.customException.BookingFailedException;
import com.srbms.customException.NoBookingFoundException;
import com.srbms.dto.Booking;
import com.srbms.dto.Resource;
import com.srbms.service.BookingService;
import com.srbms.service.BookingServiceImplementation;
import com.srbms.service.ResourceService;
import com.srbms.service.ResourceServiceImplementation;
import com.srbms.util.CollectionUtil;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingServiceImplementationTest {

    private static final String userId = "U001";
    private static BookingService bookingService;
    private static ResourceService resourceService;

    @BeforeAll
    static void init() {
        CollectionUtil.bookingRepo.clear();
        CollectionUtil.resourceRepo.clear();
        bookingService = new BookingServiceImplementation(userId);
        resourceService = new ResourceServiceImplementation();
    }

    @Test
    @Order(1)
    void testBookResources_Success() throws Exception {
        Resource r1 = new Resource("R001", "Laptop", "Electronics", 500.0, true);
        resourceService.addResource(r1);

        List<Resource> toBook = new ArrayList<>();
        toBook.add(resourceService.getResource("R001"));

        boolean booked = bookingService.bookResources(userId, toBook,
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));

        assertTrue(booked);
        assertEquals(1, bookingService.seeBookings(userId).size());
    }

    @Test
    @Order(2)
    void testBookResources_EmptyResourceList() {
        List<Resource> emptyList = new ArrayList<>();
        BookingFailedException exception = assertThrows(
                BookingFailedException.class,
                () -> bookingService.bookResources(userId, emptyList,
                        LocalDate.now().plusDays(1), LocalDate.now().plusDays(2))
        );
        assertEquals("No resources selected for booking.", exception.getMessage());
    }

    @Test
    @Order(3)
    void testBookResources_EndDateBeforeStart() {
        List<Resource> list = new ArrayList<>();
        list.add(new Resource("R002", "Monitor", "Electronics", 300.0, true));
        BookingFailedException exception = assertThrows(
                BookingFailedException.class,
                () -> bookingService.bookResources(userId, list,
                        LocalDate.now().plusDays(3), LocalDate.now().plusDays(1))
        );
        assertEquals("End date cannot be before start date.", exception.getMessage());
    }

    @Test
    @Order(4)
    void testCancelBooking_Success() throws Exception {
        List<Booking> userBookings = bookingService.seeBookings(userId);
        assertFalse(userBookings.isEmpty());

        String bookingId = userBookings.get(0).getBookingID();
        assertTrue(bookingService.cancelBooking(userId, bookingId));
    }

    @Test
    @Order(5)
    void testCancelBooking_NotFound() {
        NoBookingFoundException exception = assertThrows(
                NoBookingFoundException.class,
                () -> bookingService.cancelBooking(userId, "INVALID_ID")
        );
        assertTrue(exception.getMessage().contains("No matching booking found"));
    }

    @Test
    @Order(6)
    void testViewAllBookings() {
        List<Booking> all = bookingService.viewAllBookings();
        assertNotNull(all);
    }
}

