package com.srbms.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.srbms.customException.InvalidDateException;


public class InputValidator {
	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

	public static boolean validateId(String id) {
		return id!=null && id.matches("^[A-Za-z][A-Za-z0-9]{2,10}$");
	}
	public static boolean validateName(String username) {
		return username != null && username.matches("^[A-Za-z ]{3,30}$");
	}

	public static boolean validatePassword(String password) {
		return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
	}
	public static boolean validatePhoneNumber(String phoneNumber) {
		return phoneNumber!=null && phoneNumber.matches("^[6-9]\\d{9}$");
	}
	public static boolean validateEmail(String email) {
		return email!=null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
	}
	public static boolean validateCost(double cost) {
	    return cost >= 0 && cost <= 100000; 
	}
	


	public static boolean validateDateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
		return startTime != null && endTime != null && !startTime.isAfter(endTime)
				&& !startTime.isBefore(LocalDateTime.now());
	}

	public static LocalDateTime parseDateTime(String dateTimeStr) throws InvalidDateException {
	    try {
	        return LocalDateTime.parse(dateTimeStr, FORMATTER);
	    } catch (DateTimeParseException e) {
	        throw new InvalidDateException("Invalid date format. Use yyyy-MM-ddTHH:mm (e.g., 2025-06-02T14:30)");
	    }
	}
}
