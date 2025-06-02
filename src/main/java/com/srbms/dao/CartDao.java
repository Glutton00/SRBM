package com.srbms.dao;

import com.srbms.dto.Cart;

import java.util.List;

public interface CartDao {
    boolean addCart(String userId, Cart cart);      // Create
    Cart getCartByUserId(String userId);            // Read
    List<Cart> getAllCarts();                       // Read All
    boolean updateCart(String userId, Cart cart);   // Update
    boolean deleteCart(String userId);              // Delete
}

