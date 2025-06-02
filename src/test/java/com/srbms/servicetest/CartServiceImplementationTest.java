package com.srbms.servicetest;

import com.srbms.dto.Resource;
import com.srbms.service.CartService;
import com.srbms.service.CartServiceImplementation;
import com.srbms.util.CollectionUtil;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartServiceImplementationTest {

    private static CartService cartService;
    private static final String TEST_USER_ID = "U001";

    @BeforeAll
    static void setup() {
        CollectionUtil.cartRepo.clear();
        CollectionUtil.resourceRepo.clear();
        cartService = new CartServiceImplementation(TEST_USER_ID);
    }

    @Test
    @Order(1)
    void testAddToCart_Success() {
        Resource resource = new Resource("R001", "Laptop", "Electronics", 1000.0, true);
        boolean added = cartService.addToCart(TEST_USER_ID, resource);
        assertTrue(added);
        assertEquals(1, cartService.viewCart(TEST_USER_ID).size());
    }

    @Test
    @Order(2)
    void testAddToCart_Duplicate() {
        Resource duplicate = new Resource("R001", "Laptop", "Electronics", 1000.0, true);
        boolean added = cartService.addToCart(TEST_USER_ID, duplicate);
        assertFalse(added);
    }

    @Test
    @Order(3)
    void testAddToCart_UnavailableResource() {
        Resource unavailable = new Resource("R002", "Projector", "Electronics", 800.0, false);
        boolean added = cartService.addToCart(TEST_USER_ID, unavailable);
        assertFalse(added);
    }

    @Test
    @Order(4)
    void testViewCart() {
        List<Resource> cartItems = cartService.viewCart(TEST_USER_ID);
        assertNotNull(cartItems);
        assertEquals(1, cartItems.size());
    }

    @Test
    @Order(5)
    void testGetTotalCost() {
        double cost = cartService.getTotalCost(TEST_USER_ID);
        assertEquals(1000.0, cost);
    }

    @Test
    @Order(6)
    void testRemoveCartItem_Success() {
        Resource toRemove = new Resource("R001", "Laptop", "Electronics", 1000.0, true);
        boolean removed = cartService.removeCartItem(TEST_USER_ID, toRemove);
        assertTrue(removed);
        assertEquals(0, cartService.viewCart(TEST_USER_ID).size());
    }

    @Test
    @Order(7)
    void testClearCart() {
        Resource resource1 = new Resource("R003", "Monitor", "Electronics", 300.0, true);
        Resource resource2 = new Resource("R004", "Keyboard", "Accessory", 50.0, true);

        cartService.addToCart(TEST_USER_ID, resource1);
        cartService.addToCart(TEST_USER_ID, resource2);

        assertEquals(2, cartService.viewCart(TEST_USER_ID).size());

        boolean cleared = cartService.clearCart(TEST_USER_ID);
        assertTrue(cleared);
        assertEquals(0, cartService.viewCart(TEST_USER_ID).size());
    }
}
