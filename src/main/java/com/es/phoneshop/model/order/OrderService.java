package com.es.phoneshop.model.order;

import com.es.phoneshop.model.beans.UserBean;
import com.es.phoneshop.model.cart.Cart;

public interface OrderService {
    Order placeOrder(Cart cart, UserBean userBean);
}
