package com.srbms.service;

import com.srbms.dto.Cart;
import com.srbms.dto.Resource;

import java.util.List;

public interface CartService {
    Cart getCart(String userId);
    List<Resource> viewCart(String userId);
    boolean addToCart(String userId, Resource resource);
    boolean removeCartItem(String userId, Resource resource);
    boolean clearCart(String userId);
    double getTotalCost(String userId);
}

