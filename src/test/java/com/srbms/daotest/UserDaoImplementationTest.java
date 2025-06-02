package com.srbms.daotest;

import com.srbms.dao.UserDao;
import com.srbms.dao.UserDaoImplementation;
import com.srbms.dto.RegularUser;
import com.srbms.dto.User;
import com.srbms.util.CollectionUtil;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoImplementationTest {

    private static UserDao userDao;
    private static final String USER_ID = "U001";
    private static User testUser;

    @BeforeAll
    static void setup() {
        userDao = new UserDaoImplementation();
        CollectionUtil.userRepo.clear();  // Ensure a clean state

        testUser = new RegularUser(USER_ID, "John Doe", "1234567890", "john@example.com", "Pass@123");
    }

    @Test
    @Order(1)
    void testAddUser_Success() {
        boolean added = userDao.addUser(testUser);
        assertTrue(added);
        assertEquals(1, CollectionUtil.userRepo.size());
    }

    @Test
    @Order(2)
    void testAddUser_AlreadyExists() {
        boolean added = userDao.addUser(testUser);
        assertFalse(added);
    }

    @Test
    @Order(3)
    void testGetUserById_Found() {
        User user = userDao.getUserById(USER_ID);
        assertNotNull(user);
        assertEquals("John Doe", user.getUserName());
    }

    @Test
    @Order(4)
    void testGetUserById_NotFound() {
        User user = userDao.getUserById("UNKNOWN");
        assertNull(user);
    }

    @Test
    @Order(5)
    void testGetAllUsers() {
        List<User> allUsers = userDao.getAllUsers();
        assertEquals(1, allUsers.size());
    }

    @Test
    @Order(6)
    void testUpdateUser_Success() {
        User updatedUser = new RegularUser(USER_ID, "John Updated", "9876543210", "john@example.com", "NewPass@123");
        boolean updated = userDao.updateUser(updatedUser);
        assertTrue(updated);

        User fetched = userDao.getUserById(USER_ID);
        assertEquals("John Updated", fetched.getUserName());
    }

    @Test
    @Order(7)
    void testUpdateUser_NotFound() {
        User newUser = new RegularUser("U999", "Ghost", "0000000000", "ghost@example.com", "Ghost@123");
        assertFalse(userDao.updateUser(newUser));
    }

    @Test
    @Order(8)
    void testDeleteUser_Success() {
        assertTrue(userDao.deleteUser(USER_ID));
        assertNull(userDao.getUserById(USER_ID));
    }

    @Test
    @Order(9)
    void testDeleteUser_NotFound() {
        assertFalse(userDao.deleteUser("NON_EXISTENT"));
    }
}

