package com.srbms.ui;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.srbms.customException.BookingFailedException;
import com.srbms.customException.CartEmptyException;
import com.srbms.customException.InvalidCredentialsException;
import com.srbms.customException.InvalidDateException;
import com.srbms.customException.NoBookingFoundException;
import com.srbms.customException.NoUserLoggedInException;
import com.srbms.customException.ResourceAlreadyExistsException;
import com.srbms.customException.ResourceNotFoundException;
import com.srbms.customException.UserAlreadyExistWithEmailException;
import com.srbms.dao.ResourceDao;
import com.srbms.dao.ResourceDaoImplementation;
import com.srbms.dao.UserDao;
import com.srbms.dao.UserDaoImplementation;
import com.srbms.dto.Admin;
import com.srbms.dto.RegularUser;
import com.srbms.dto.Resource;
import com.srbms.dto.ResourceManager;
import com.srbms.dto.User;
import com.srbms.service.BookingService;
import com.srbms.service.BookingServiceImplementation;
import com.srbms.service.CartService;
import com.srbms.service.CartServiceImplementation;
import com.srbms.service.ResourceService;
import com.srbms.service.ResourceServiceImplementation;
import com.srbms.service.UserService;
import com.srbms.service.UserServiceImplementation;
import com.srbms.util.CollectionUtil;
import com.srbms.util.InputValidator;

public class UI {
//	private User loggedInUser;
	private final Scanner scanner = new Scanner(System.in);
	private final UserService userService = new UserServiceImplementation();

	public void show() throws Exception {
		seedDummyResources();
		seedDummyUsers();
		while (true) {

			System.out.println("\n===== Welcome to Smart Resource Booking System =====");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Exit");
			System.out.print("Enter your choice: ");

			String choice = scanner.nextLine();

			switch (choice) {
			case "1":
				handleRegistration();
				break;
			case "2":
				handleLogin();
				break;
			case "3":
				System.out.println("Thank you for using the system. Goodbye!");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	public void showMenu(User user) {
//		UserServiceImplementation userserviceImplementation = new UserServiceImplementation();
		CartService cartService = new CartServiceImplementation(user.getUserID());
		BookingService bookingService = new BookingServiceImplementation(user.getUserID());
		ResourceService resourceService = new ResourceServiceImplementation();
		UserServiceImplementation userserviceImplementation1 = (UserServiceImplementation) userService;
		Scanner adminScanner = new Scanner(System.in);

		if (user.getRole().equals("REGULAR_USER")) {
			while (true) {
				System.out.println("\n--- Regular User Menu ---");
				System.out.println("1. View My Bookings");
				System.out.println("2. View My Cart");
				System.out.println("3. Add Resource to Cart");
				System.out.println("4. View Available Resources");
				System.out.println("5. Clear My Cart");
				System.out.println("6. View Cost of Cart per day");
				System.out.println("7. Book Resources in the cart");
				System.out.println("8. cancel booking");
				System.out.println("9. Logout");
				System.out.print("Enter your choice: ");

				int choice;
				try {
					choice = Integer.parseInt(scanner.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("Invalid input. Please enter a number.");
					continue;
				}

				switch (choice) {
				case 1:
					userserviceImplementation1.viewMyBooking(bookingService);
					break;
				case 2:
					userserviceImplementation1.viewMyCart(cartService);
					break;
				case 3:
					try {
						userserviceImplementation1.addResourceToCart(resourceService, cartService);
					} catch (ResourceNotFoundException e) {
						System.out.println("Error: " + e.getMessage());
					}
					break;
				case 4:
					userserviceImplementation1.viewResources(resourceService);
					break;
				case 5:
					userserviceImplementation1.clearCart(cartService);
					break;
				case 6:
					userserviceImplementation1.viewCostPerDayofCart(cartService);
					break;
				case 7:
					try {
						userserviceImplementation1.bookResourcesInCart(cartService, bookingService);
					} catch (CartEmptyException | BookingFailedException e) {
						System.out.println("Booking failed: " + e.getMessage());
					} catch (DateTimeParseException e) {
						System.out.println("Invalid date-time input.");
					} catch (InvalidDateException e) {
						System.out.println(e.getMessage());
					} catch (Exception e) {
						System.out.println("An unexpected error occurred during booking: " + e.getMessage());
					}
					break;
				case 8:
					try {
						userserviceImplementation1.cancelBooking(bookingService);
					} catch (NoBookingFoundException e) {
						System.out.println("Cancel failed: " + e.getMessage());
					}
					break;
				case 9:
					try {
						userService.logOutUser(user.getUserID());
					} catch (NoUserLoggedInException e) {
						System.out.println("Logout failed: " + e.getMessage());
					}
					return;
				default:
					System.out.println(" Invalid choice. Try again.");
				}
			}
		} else if (user.getRole().equals("ADMIN")) {

			while (true) {
				System.out.println("\n--- Admin Menu ---");
				System.out.println("1. Add User");
				System.out.println("2. Delete User");
				System.out.println("3. View All Bookings");
				System.out.println("4. View All Resources");
				System.out.println("5. View All Users");
				System.out.println("6. Logout");
				System.out.print("Enter your choice: ");

				int adminChoice = adminScanner.nextInt();
				adminScanner.nextLine(); // consume new line..

				switch (adminChoice) {
				case 1:
					String id;
					do {
						System.out.print("Enter User ID: ");
						id = scanner.nextLine();
						if (!InputValidator.validateId(id)) {
							System.out.println("Invalid ID. Please try again.");
						}
					} while (!InputValidator.validateId(id));

					String name;
					do {
						System.out.print("Enter Name: ");
						name = scanner.nextLine();
						if (!InputValidator.validateName(name)) {
							System.out.println("Invalid name. Please enter alphabetic characters only.");
						}
					} while (!InputValidator.validateName(name));

					String phone;
					do {
						System.out.print("Enter Phone Number: ");
						phone = scanner.nextLine();
						if (!InputValidator.validatePhoneNumber(phone)) {
							System.out.println("Invalid phone number. Must be 10 digits.");
						}
					} while (!InputValidator.validatePhoneNumber(phone));

					String email;
					do {
						System.out.print("Enter Email: ");
						email = scanner.nextLine();
						if (!InputValidator.validateEmail(email)) {
							System.out.println("Invalid email format.");
						}
					} while (!InputValidator.validateEmail(email));

					String password;
					do {
						System.out.print("Enter Password: ");
						password = scanner.nextLine();
						if (!InputValidator.validatePassword(password)) {
							System.out.println(
									"Invalid password. Use a mix of uppercase, lowercase, digits, and special characters.");
						}
					} while (!InputValidator.validatePassword(password));

					User newUser = new RegularUser(id, name, phone, email, password);
					try {
						boolean registered = userService.registerUser(newUser);
						if (registered) {
							System.out.println("Registration successful.");
						}
					} catch (UserAlreadyExistWithEmailException e) {
						System.out.println("Registration failed: " + e.getMessage());
					}
					break;
				case 2:
					String userID;
					do {
						System.out.print("Enter User ID: ");
						userID = scanner.nextLine();
						if (!InputValidator.validateId(userID)) {
							System.out.println("Invalid ID. Please try again.");
						}
					} while (!InputValidator.validateId(userID));
					if (userService.removeUser(userID)) {
						System.out.println("user is removed");
					}
					break;
				case 3:
					bookingService.viewAllBookings().forEach((item) -> {
						System.out.println(item);
					});
					break;
				case 4:
					resourceService.getAllResources().forEach((item) -> {
						System.out.println(item);
					});
					break;
				case 5:
					userService.getAllUsers().forEach((item) -> {
						System.out.println(item);
					});
					break;
				case 6:
					try {
						userService.logOutUser(user.getUserID());
					} catch (NoUserLoggedInException e) {
						System.out.println("Logout failed: " + e.getMessage());
					}
					return;
				default:
					System.out.println("Invalid choice. Try again.");
				}
			}
		} else if (user.getRole().equals("RESOURCE_MANAGER")) {

			while (true) {
				System.out.println("\n--- Resource Manager ---");
				System.out.println("1. Add Resource");
				System.out.println("2. Delete Resource");
				System.out.println("3. View All Resources");
				System.out.println("4. update Resources");
				System.out.println("5. Logout");
				System.out.print("Enter your choice: ");

				int adminChoice = adminScanner.nextInt();
				adminScanner.nextLine(); // consume new line..
				switch (adminChoice) {
				case 1:
					String id;
					do {
						System.out.print("Enter Resource ID ");
						id = scanner.nextLine();
						if (!InputValidator.validateId(id)) {
							System.out.println("Invalid Resource Id.");
						}
					} while (!InputValidator.validateId(id));

					String name;
					do {
						System.out.print("Enter Resource Name ");
						name = scanner.nextLine();
						if (!InputValidator.validateName(name)) {
							System.out.println("Invalid Resource Name.");
						}
					} while (!InputValidator.validateName(name));

					String type;
					do {
						System.out.print("Enter Resource Type ");
						type = scanner.nextLine();
						if (!InputValidator.validateName(type)) {
							System.out.println("Invalid Resource Type.");
						}
					} while (!InputValidator.validateName(type));

					double cost = 0;
					boolean validCost = false;
					do {
						System.out.print("Enter Resource Cost: ");
						String costInput = scanner.nextLine();
						try {
							cost = Double.parseDouble(costInput);
							if (!InputValidator.validateCost(cost)) {
								System.out.println("Invalid Resource Cost.");
							} else {
								validCost = true;
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid input. Please enter a numeric value for cost.");
						}
					} while (!validCost);

					System.out.print("Is Resource Available? (true/false): ");
					boolean isAvailable = Boolean.parseBoolean(scanner.nextLine());

					// Create resource
					Resource newResource = new Resource(id, name, type, cost, isAvailable);

					try {
						if (resourceService.addResource(newResource)) {
							System.out.println("Resource is added.");
						}
					} catch (ResourceAlreadyExistsException e) {
						System.out.println("Add failed: " + e.getMessage());
					}
					break;
				case 2:
					String resId;
					do {
						System.out.print("Enter Resource ID ");
						resId = scanner.nextLine();
						if (!InputValidator.validateId(resId)) {
							System.out.println("Invalid Resource Id.");
						}
					} while (!InputValidator.validateId(resId));

					try {
						if (resourceService.removeResource(resId)) {
							System.out.println("Resource removed successfully.");
						}
					} catch (ResourceNotFoundException e) {
						System.out.println("Remove failed: " + e.getMessage());
					}
					break;
				case 3:
					resourceService.getAllResources().forEach((item) -> {
						System.out.println(item);
					});
					break;
				case 4:
					String upId;
					do {
						System.out.print("Enter Resource ID ");
						upId = scanner.nextLine();
						if (!InputValidator.validateId(upId)) {
							System.out.println("Invalid Resource Id.");
						}
					} while (!InputValidator.validateId(upId));

					String upName;
					do {
						System.out.print("Enter Resource Name ");
						upName = scanner.nextLine();
						if (!InputValidator.validateName(upName)) {
							System.out.println("Invalid Resource Name.");
						}
					} while (!InputValidator.validateName(upName));

					String upType;
					do {
						System.out.print("Enter Resource Type ");
						upType = scanner.nextLine();
						if (!InputValidator.validateName(upType)) {
							System.out.println("Invalid Resource Type.");
						}
					} while (!InputValidator.validateName(upType));

					double upCost = 0;
					boolean validUpCost = false;
					do {
						System.out.print("Enter Resource Cost: ");
						String upCostInput = scanner.nextLine();
						try {
							upCost = Double.parseDouble(upCostInput);
							if (!InputValidator.validateCost(upCost)) {
								System.out.println("Invalid Resource Cost.");
							} else {
								validUpCost = true;
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid input. Please enter a numeric value for cost.");
						}
					} while (!validUpCost);

					System.out.print("Is Resource Available? (true/false): ");
					boolean upIsAvailable = Boolean.parseBoolean(scanner.nextLine());

					// Create resource
					Resource upResource = new Resource(upId, upName, upType, upCost, upIsAvailable);
					try {
						if (resourceService.updateResource(upResource)) {
							System.out.println("Resource updated successfully.");
						}
					} catch (ResourceNotFoundException e) {
						System.out.println("Update failed: " + e.getMessage());
					}
					break;
				case 5:
					try {
						userService.logOutUser(user.getUserID());
					} catch (NoUserLoggedInException e) {
						System.out.println("Logout failed: " + e.getMessage());
					}
					return;
				default:
					System.out.println("Invalid choice. Try again.");
				}
			}
		} else {
			System.out.println("Unsupported role for this menu.");

		}
	}

	private void handleRegistration() throws Exception {
		System.out.println("\n--- User Registration ---");

		String id;
		do {
			System.out.print("Enter User ID: ");
			id = scanner.nextLine();
			if (!InputValidator.validateId(id)) {
				System.out.println("Invalid ID. Please try again.");
			}
		} while (!InputValidator.validateId(id));

		String name;
		do {
			System.out.print("Enter Name: ");
			name = scanner.nextLine();
			if (!InputValidator.validateName(name)) {
				System.out.println("Invalid name. Please enter alphabetic characters only.");
			}
		} while (!InputValidator.validateName(name));

		String phone;
		do {
			System.out.print("Enter Phone Number: ");
			phone = scanner.nextLine();
			if (!InputValidator.validatePhoneNumber(phone)) {
				System.out.println("Invalid phone number. Must be 10 digits.");
			}
		} while (!InputValidator.validatePhoneNumber(phone));

		String email;
		do {
			System.out.print("Enter Email: ");
			email = scanner.nextLine();
			if (!InputValidator.validateEmail(email)) {
				System.out.println("Invalid email format.");
			}
		} while (!InputValidator.validateEmail(email));

		String password;
		do {
			System.out.print("Enter Password: ");
			password = scanner.nextLine();
			if (!InputValidator.validatePassword(password)) {
				System.out.println(
						"Invalid password. Use a mix of uppercase, lowercase, digits, and special characters.");
			}
		} while (!InputValidator.validatePassword(password));

		User newUser = new RegularUser(id, name, phone, email, password);
		boolean registered = userService.registerUser(newUser);

		if (registered) {
			System.out.println("Registration successful.");
		} else {
			System.out.println("Registration failed. User might already exist.");
		}
	}

	private void handleLogin() throws Exception {
		System.out.println("\n--- User Login ---");

		String email;
		do {
			System.out.print("Enter Email: ");
			email = scanner.nextLine();
			if (!InputValidator.validateEmail(email)) {
				System.out.println("Invalid email format.");
			}
		} while (!InputValidator.validateEmail(email));

		String password;
		do {
			System.out.print("Enter Password: ");
			password = scanner.nextLine();
			if (!InputValidator.validatePassword(password)) {
				System.out.println("Invalid password format.");
			}
		} while (!InputValidator.validatePassword(password));

		try {
			boolean success = userService.loginUser(email, password);
			if (success) {
				for (User user : CollectionUtil.userRepo) {
					if (user.getUserEmail().equals(email)) {
						showMenu(user);
						break;
					}
				}
			}
		} catch (InvalidCredentialsException e) {
			System.out.println(e.getMessage());
		}

	}

	private void seedDummyUsers() {
		UserDao userDao = new UserDaoImplementation();

		userDao.addUser(new Admin("A101", "Admin", "8767876545", "admin@gmail.com", "Admin@123"));
		userDao.addUser(new ResourceManager("RM101", "Ramesh", "9876545454", "ResourceManage@gmail.com", "Resou@123"));
		userDao.addUser(new RegularUser("U101", "Alice Smith", "9876543210", "alice@example.com", "Pass@123"));
		userDao.addUser(new RegularUser("U102", "Bob Johnson", "9876543211", "bob@example.com", "Pass@123"));
		userDao.addUser(new RegularUser("U103", "Charlie Brown", "9876543212", "charlie@example.com", "Pass@345"));
		userDao.addUser(new RegularUser("U104", "Diana Prince", "9876543213", "diana@example.com", "Pass@456"));
		userDao.addUser(new RegularUser("U105", "Ethan Hunt", "9876543214", "ethan@example.com", "Pass@567"));
		userDao.addUser(new RegularUser("U106", "Fiona Glenanne", "9876543215", "fiona@example.com", "Pass@678"));
		userDao.addUser(new RegularUser("U107", "George Clark", "9876543216", "george@example.com", "Pass@789"));
		userDao.addUser(new RegularUser("U108", "Hannah Lee", "9876543217", "hannah@example.com", "Pass@890"));
		userDao.addUser(new RegularUser("U109", "Ian Wright", "9876543218", "ian@example.com", "Pass@901"));
		userDao.addUser(new RegularUser("U110", "Jane Doe", "9876543219", "jane@example.com", "Pass@012"));

		System.out.println("Dummy users seeded successfully.");
	}

	private void seedDummyResources() {
		ResourceDao resourceDao = new ResourceDaoImplementation();

		resourceDao.addResource(new Resource("R101", "Projector", "Electronics", 500.0, true));
		resourceDao.addResource(new Resource("R102", "Whiteboard", "Furniture", 150.0, true));
		resourceDao.addResource(new Resource("R103", "Conference Room", "Meeting Room", 1000.0, true));
		resourceDao.addResource(new Resource("R104", "Laptop", "Electronics", 1200.0, false));
		resourceDao.addResource(new Resource("R105", "Desktop", "Electronics", 900.0, true));
		resourceDao.addResource(new Resource("R106", "Printer", "Electronics", 300.0, true));
		resourceDao.addResource(new Resource("R107", "Scanner", "Electronics", 250.0, false));
		resourceDao.addResource(new Resource("R108", "Tablet", "Electronics", 450.0, true));
		resourceDao.addResource(new Resource("R109", "Speaker", "Accessory", 100.0, true));
		resourceDao.addResource(new Resource("R110", "Microphone", "Accessory", 80.0, true));
		resourceDao.addResource(new Resource("R111", "Camera", "Electronics", 700.0, true));
		resourceDao.addResource(new Resource("R112", "Tripod", "Accessory", 60.0, true));
		resourceDao.addResource(new Resource("R113", "Router", "Networking", 120.0, true));
		resourceDao.addResource(new Resource("R114", "Switch", "Networking", 110.0, false));
		resourceDao.addResource(new Resource("R115", "Monitor", "Electronics", 250.0, true));
		resourceDao.addResource(new Resource("R116", "Keyboard", "Accessory", 40.0, true));
		resourceDao.addResource(new Resource("R117", "Mouse", "Accessory", 30.0, true));
		resourceDao.addResource(new Resource("R118", "VR Headset", "Electronics", 650.0, true));
		resourceDao.addResource(new Resource("R119", "3D Printer", "Electronics", 2000.0, false));
		resourceDao.addResource(new Resource("R120", "Smart TV", "Electronics", 1500.0, true));

		System.out.println("Dummy resources seeded successfully.");
	}

}
