package com.srbms.daotest;

import com.srbms.dao.BookingDao;
import com.srbms.dao.BookingDaoImplementation;
import com.srbms.dto.Booking;
import com.srbms.dto.Resource;
import com.srbms.util.CollectionUtil;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingDaoImplementationTest {

    private static BookingDao bookingDao;
    private static String userId;
    private static Booking booking;

    @BeforeAll
    static void setup() {
        bookingDao = new BookingDaoImplementation();
        userId = "U001";
        CollectionUtil.bookingRepo.clear();

        // Create test booking
        List<Resource> resources = new ArrayList<>();
        resources.add(new Resource("R001", "Laptop", "Electronics", 1000.0, true));
        booking = new Booking("B001", LocalDate.now(), LocalDate.now().plusDays(2), resources, 2000.0);
    }

    @Test
    @Order(1)
    void testAddBooking_Success() {
        boolean added = bookingDao.addBooking(userId, booking);
        assertTrue(added);
        assertNotNull(CollectionUtil.bookingRepo.get(userId));
        assertEquals(1, CollectionUtil.bookingRepo.get(userId).size());
    }

    @Test
    @Order(2)
    void testAddBooking_DuplicateFails() {
        Booking anotherBooking = new Booking("B002", LocalDate.now(), LocalDate.now().plusDays(1), new ArrayList<>(), 1000.0);
        boolean result = bookingDao.addBooking(userId, anotherBooking);
        assertFalse(result);  // user already exists
    }

    @Test
    @Order(3)
    void testViewBooking_Success() {
        List<Booking> bookings = bookingDao.viewBooking(userId);
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
        assertEquals("B001", bookings.get(0).getBookingID());
    }

    @Test
    @Order(4)
    void testViewBooking_NoUser() {
        List<Booking> bookings = bookingDao.viewBooking("UNKNOWN");
        assertNull(bookings);
    }

    @Test
    @Order(5)
    void testViewAllBookings() {
        List<Booking> allBookings = bookingDao.viewAllBookings();
        assertEquals(1, allBookings.size());
        assertEquals("B001", allBookings.get(0).getBookingID());
    }

    @Test
    @Order(6)
    void testCancelBooking_Success() {
        boolean cancelled = bookingDao.cancelBooking(userId);
        assertTrue(cancelled);
        assertNull(CollectionUtil.bookingRepo.get(userId));
    }

    @Test
    @Order(7)
    void testCancelBooking_NoUser() {
        boolean cancelled = bookingDao.cancelBooking("UNKNOWN");
        assertFalse(cancelled);
    }
}

