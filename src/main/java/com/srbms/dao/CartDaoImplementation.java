package com.srbms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.srbms.dto.Cart;
import com.srbms.util.CollectionUtil;

public class CartDaoImplementation implements CartDao {

    private Map<String, Cart> cartRepo = CollectionUtil.cartRepo;

    @Override
    public boolean addCart(String userId, Cart cart) {
        if (!cartRepo.containsKey(userId)) {
            cartRepo.put(userId, cart);
            return true;
        }
        return false;
    }

    @Override
    public Cart getCartByUserId(String userId) {
        return cartRepo.get(userId);
    }

    @Override
    public List<Cart> getAllCarts() {
        return new ArrayList<>(cartRepo.values());
    }

    @Override
    public boolean updateCart(String userId, Cart cart) {
        if (cartRepo.containsKey(userId)) {
            cartRepo.put(userId, cart);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCart(String userId) {
        if (cartRepo.containsKey(userId)) {
            cartRepo.remove(userId);
            return true;
        }
        return false;
    }
}

