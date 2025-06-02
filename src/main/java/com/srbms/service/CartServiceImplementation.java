package com.srbms.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.srbms.dto.Cart;
import com.srbms.dto.Resource;
import com.srbms.util.CollectionUtil;

public class CartServiceImplementation implements CartService {

    private final Map<String, Cart> cartRepo = CollectionUtil.cartRepo;
    private final String userId;
    private Cart cart;

    public CartServiceImplementation(String userId) {
        this.userId = userId;
        if (cartRepo.containsKey(userId)) {
            this.cart = cartRepo.get(userId);
        } else {
            this.cart = new Cart(userId, new ArrayList<>(), 0);
            cartRepo.put(userId, this.cart);
        }
    }

    @Override
    public Cart getCart(String userId) {
        return cart;
    }

    @Override
    public List<Resource> viewCart(String userId) {
        return cart.getCartItems();
    }

    @Override
    public boolean addToCart(String userId, Resource resource) {
        // Check if resource is available
        if (!resource.isResourceIsAvailable()) {
            System.out.println("Resource '" + resource.getResourceName() + "' is not available.");
            return false;
        }

        List<Resource> items = cart.getCartItems();

        // Check for duplicates
        if (!items.contains(resource)) {
            items.add(resource);
            cart.setNoOfItems(items.size());
            cartRepo.put(userId, cart);  // Update central repository
            return true;
        } else {
            System.out.println("Resource already exists in cart.");
            return false;
        }
    }

    @Override
    public boolean removeCartItem(String userId, Resource resource) {
        List<Resource> items = cart.getCartItems();
        if (items.remove(resource)) {
            cart.setNoOfItems(items.size());
            cartRepo.put(userId, cart);
            return true;
        }
        return false;
    }

    @Override
    public boolean clearCart(String userId) {
        cart.getCartItems().clear();
        cart.setNoOfItems(0);
        cartRepo.put(userId, cart);
        return true;
    }

    @Override
    public double getTotalCost(String userId) {
        return cart.getCartItems().stream()
                .mapToDouble(Resource::getResourceCost)
                .sum();
    }
}


