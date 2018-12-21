package com.es.phoneshop.web;

import com.es.phoneshop.model.UserBean;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.ArrayListProductDao;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static volatile OrderServiceImpl instance;

    public static OrderServiceImpl getInstance() {
        OrderServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (ArrayListProductDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new OrderServiceImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Order placeOrder(Cart cart, UserBean userBean){
        Order order = new Order();
        order.setName(userBean.getName());
        order.setDeliveryAddress(userBean.getDeliveryAddress());
        order.setPhone(userBean.getPhone());
       ArrayListOrderDao.getInstance().save(order);
       return order;
    }
}
