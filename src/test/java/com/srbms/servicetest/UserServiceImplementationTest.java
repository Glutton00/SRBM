package com.srbms.servicetest;

import com.srbms.customException.*;
import com.srbms.dto.RegularUser;
import com.srbms.dto.User;
import com.srbms.service.UserService;
import com.srbms.service.UserServiceImplementation;
import com.srbms.util.CollectionUtil;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceImplementationTest {

    private static UserService userService;

    @BeforeAll
    static void setUp() {
        userService = new UserServiceImplementation();
        CollectionUtil.userRepo.clear(); // Reset repo
    }

    @Test
    @Order(1)
    void testRegisterUser_Success() throws UserAlreadyExistWithEmailException {
        User user = new RegularUser("U001", "John", "9876543210", "john@example.com", "Pass@123");
        assertTrue(userService.registerUser(user));
    }

    @Test
    @Order(2)
    void testRegisterUser_AlreadyExists() {
        User user = new RegularUser("U001", "John", "9876543210", "john@example.com", "Pass@123");
        assertThrows(UserAlreadyExistWithEmailException.class, () -> userService.registerUser(user));
    }

    @Test
    @Order(3)
    void testLoginUser_Success() throws InvalidCredentialsException {
        assertTrue(userService.loginUser("john@example.com", "Pass@123"));
    }

    @Test
    @Order(4)
    void testLoginUser_InvalidCredentials() {
        assertThrows(InvalidCredentialsException.class, () -> userService.loginUser("wrong@example.com", "Pass@123"));
    }

    @Test
    @Order(5)
    void testLogoutUser_Success() throws Exception {
        userService.loginUser("john@example.com", "Pass@123");
        assertDoesNotThrow(() -> userService.logOutUser("U001"));
    }

    @Test
    @Order(6)
    void testLogoutUser_NoUserLoggedIn() {
        assertThrows(NoUserLoggedInException.class, () -> userService.logOutUser("U001"));
    }

    @Test
    @Order(7)
    void testGetAllUsers() throws UserAlreadyExistWithEmailException {
        userService.registerUser(new RegularUser("U002", "Alice", "1234567890", "alice@example.com", "Pass@123"));
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    @Order(8)
    void testRemoveUser() {
        assertTrue(userService.removeUser("U002"));
    }

}

