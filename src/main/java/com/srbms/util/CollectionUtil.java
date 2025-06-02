package com.srbms.util;

import com.srbms.dto.Booking;
import com.srbms.dto.Cart;
import com.srbms.dto.Resource;
import com.srbms.dto.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionUtil {
    public static Map<String, Cart> cartRepo = new HashMap<>();
    public static Map<String, List<Booking>> bookingRepo = new HashMap<>();
    public static ArrayList<User> userRepo = new ArrayList<>();
    public static ArrayList<Resource> resourceRepo = new ArrayList<>();
}
