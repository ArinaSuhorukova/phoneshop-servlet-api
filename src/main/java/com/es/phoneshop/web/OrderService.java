package com.es.phoneshop.web;

import com.es.phoneshop.model.UserBean;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;

public interface OrderService {
    Order placeOrder(Cart cart, UserBean userBean);
}
