package com.srbms.dto;

import java.util.ArrayList;

public class Cart {
    private String cartID;
    private ArrayList<Resource> cartItems;
    private int noOfItems;

    // Constructor
    public Cart(String cartID, ArrayList<Resource> cartItems, int noOfItems) {
        this.cartID = cartID;
        this.cartItems = cartItems;
        this.noOfItems = noOfItems;
    }

    // Getters and Setters
    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public ArrayList<Resource> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<Resource> cartItems) {
        this.cartItems = cartItems;
    }

    public int getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(int noOfItems) {
        this.noOfItems = noOfItems;
    }
}
