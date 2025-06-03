package com.srbms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.srbms.customException.BookingFailedException;
import com.srbms.customException.CartEmptyException;
import com.srbms.customException.InvalidCredentialsException;
import com.srbms.customException.InvalidDateException;
import com.srbms.customException.NoBookingFoundException;
import com.srbms.customException.NoUserLoggedInException;
import com.srbms.customException.ResourceNotFoundException;
import com.srbms.customException.UserAlreadyExistWithEmailException;
import com.srbms.dao.UserDaoImplementation;
import com.srbms.dto.Resource;
import com.srbms.dto.User;
import com.srbms.util.CollectionUtil;
import com.srbms.util.InputValidator;


public class UserServiceImplementation implements UserService {
	private User loggedInUser;
	
	private final UserDaoImplementation userDao = new UserDaoImplementation();
	private final Scanner scanner = new Scanner(System.in);
	private List<Resource> resourcesToBook;

	@Override
	public boolean registerUser(User user) throws UserAlreadyExistWithEmailException {
		for (User existingUser : CollectionUtil.userRepo) {
			if (existingUser.getUserEmail().equalsIgnoreCase(user.getUserEmail())) {
				throw new UserAlreadyExistWithEmailException("User already exists with email: " + user.getUserEmail());
			}
		}
		return userDao.addUser(user);
	}

	@Override
	public boolean loginUser(String email, String password) throws InvalidCredentialsException {
		for (User user : CollectionUtil.userRepo) {
			if (user.getUserEmail().equals(email) && user.getUserPassword().equals(password)) {
				loggedInUser = user;
				System.out.println("Login successful. Welcome, " + user.getUserName());
				return true;
			}
		}
	throw new InvalidCredentialsException("Invalid email or password. Please try again.");
		
	}

	@Override

	public void logOutUser(String userId) throws NoUserLoggedInException {
		if (loggedInUser == null) {
			throw new NoUserLoggedInException("No user is currently logged in.");
		}

		if (!loggedInUser.getUserID().equals(userId)) {
			throw new NoUserLoggedInException("Session mismatch. Cannot log out this user.");
		}

		System.out.println("User logged out: " + loggedInUser.getUserName());
		loggedInUser = null;
	}

	public void viewMyBooking(BookingService bookingService) {
		bookingService.seeBookings(loggedInUser.getUserID()).forEach((e) -> {
			System.out.println(e);
		});
	}

	public void viewMyCart(CartService cartService) {
		List<Resource> items = cartService.viewCart(loggedInUser.getUserID());
		if (!items.isEmpty()) {
			items.forEach((item) -> {
				System.out.println("resource id " + item.getResourceID() + " resource type " + item.getResourceType()
						+ " name: " + item.getResourceName());
			});
		} else {
			System.out.println("cart is empty");
		}
	}

	public void addResourceToCart(ResourceService resourceService, CartService cartService)
			throws ResourceNotFoundException {
		String resourceId;
		
		do {
			System.out.print("Enter Resource Id ");
			resourceId = scanner.nextLine();
			if (!InputValidator.validateId(resourceId)) {
				System.out.println("Invalid Resource Id.");
			}
		} while (!InputValidator.validateId(resourceId));

		Resource res = resourceService.getResource(resourceId);
		if (res != null) {
			cartService.addToCart(loggedInUser.getUserID(), res);
		} else {
			System.out.println("resource not found");
		}

	}

	public void viewResources(ResourceService resourceService) {
		System.out.println("Available Resources:");
		List<Resource> resources = resourceService.getAllResources().stream()
				.filter(item -> item.isResourceIsAvailable()).collect(Collectors.toList());
		for (Resource r : resources) {
			System.out.println(r);
		}
	}

	public void bookResourcesInCart(CartService cartService, BookingService bookingService)
			throws BookingFailedException, InvalidDateException, CartEmptyException {

		List<Resource> resourcesToBook = cartService.viewCart(loggedInUser.getUserID());
		if (resourcesToBook.isEmpty()) {
			throw new CartEmptyException("Your cart is empty. Cannot proceed with booking.");
		}

		System.out.print("Enter booking start date and time (yyyy-MM-ddTHH:mm): ");
		String startInput = scanner.nextLine();
		LocalDateTime startDateTime = InputValidator.parseDateTime(startInput);

		System.out.print("Enter booking end date and time (yyyy-MM-ddTHH:mm): ");
		String endInput = scanner.nextLine();
		LocalDateTime endDateTime = InputValidator.parseDateTime(endInput);

		if (startDateTime == null || endDateTime == null) {
			System.out.println("Invalid date format. Please use yyyy-MM-ddTHH:mm (e.g., 2025-06-02T14:30). ");
			return;
		}

		if (!InputValidator.validateDateTimeRange(startDateTime, endDateTime)) {
			System.out.println("Invalid range: End must be after start, and start must be in the future.");
			return;
		}

		boolean booked = bookingService.bookResources(loggedInUser.getUserID(), resourcesToBook,
				startDateTime.toLocalDate(), endDateTime.toLocalDate());

		System.out.println("Booking successful for the resources in your cart.");
		cartService.clearCart(loggedInUser.getUserID());

	}

	public void cancelBooking(BookingService bookingService) throws NoBookingFoundException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the booking id to cancel : ");
		String bookingId = sc.nextLine();

		bookingService.cancelBooking(loggedInUser.getUserID(), bookingId);

	}

	public boolean removeUser(String userID) {
		return userDao.deleteUser(userID);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
	
	public void viewCostPerDayofCart(CartService cartService){
		double cost = cartService.getTotalCost(this.loggedInUser.getUserID());
		System.out.println("Total Cart Cost: " + cost);
	}
	public void clearCart(CartService cartService)
	{
		cartService.clearCart(loggedInUser.getUserID());
	}
	
}