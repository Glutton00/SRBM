package com.srbms.daotest;

import com.srbms.dao.CartDao;
import com.srbms.dao.CartDaoImplementation;
import com.srbms.dto.Cart;
import com.srbms.dto.Resource;
import com.srbms.util.CollectionUtil;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartDaoImplementationTest {

    private static CartDao cartDao;
    private static String cartId;
    private static Cart testCart;

    @BeforeAll
    static void setup() {
        cartDao = new CartDaoImplementation();
        cartId = "C001";
        CollectionUtil.cartRepo.clear();

        ArrayList<Resource> items = new ArrayList<>();
        items.add(new Resource("R001", "Laptop", "Electronics", 1000.0, true));
        testCart = new Cart(cartId, items, items.size());
    }

    @Test
    @Order(1)
    void testAddCart_Success() {
        boolean added = cartDao.addCart(cartId, testCart);
        assertTrue(added);
        assertEquals(1, CollectionUtil.cartRepo.size());
    }

    @Test
    @Order(2)
    void testAddCart_AlreadyExists() {
        Cart duplicate = new Cart(cartId, new ArrayList<>(), 0);
        assertFalse(cartDao.addCart(cartId, duplicate));
    }

    @Test
    @Order(3)
    void testGetCartByUserId_Exists() {
        Cart fetched = cartDao.getCartByUserId(cartId);
        assertNotNull(fetched);
        assertEquals(cartId, fetched.getCartID());
        assertEquals(1, fetched.getCartItems().size());
    }

    @Test
    @Order(4)
    void testGetCartByUserId_NotFound() {
        Cart fetched = cartDao.getCartByUserId("UNKNOWN");
        assertNull(fetched);
    }

    @Test
    @Order(5)
    void testGetAllCarts() {
        List<Cart> carts = cartDao.getAllCarts();
        assertEquals(1, carts.size());
    }

    @Test
    @Order(6)
    void testUpdateCart_Success() {
        ArrayList<Resource> newItems = new ArrayList<>();
        newItems.add(new Resource("R002", "Monitor", "Electronics", 400.0, true));
        Cart updatedCart = new Cart(cartId, newItems, newItems.size());

        assertTrue(cartDao.updateCart(cartId, updatedCart));
        Cart fetched = cartDao.getCartByUserId(cartId);
        assertEquals("R002", fetched.getCartItems().get(0).getResourceID());
        assertEquals(1, fetched.getNoOfItems());
    }

    @Test
    @Order(7)
    void testUpdateCart_NotFound() {
        Cart newCart = new Cart("C999", new ArrayList<>(), 0);
        assertFalse(cartDao.updateCart("C999", newCart));
    }

    @Test
    @Order(8)
    void testDeleteCart_Success() {
        assertTrue(cartDao.deleteCart(cartId));
        assertNull(cartDao.getCartByUserId(cartId));
    }

    @Test
    @Order(9)
    void testDeleteCart_NotFound() {
        assertFalse(cartDao.deleteCart("NON_EXISTENT"));
    }
}
