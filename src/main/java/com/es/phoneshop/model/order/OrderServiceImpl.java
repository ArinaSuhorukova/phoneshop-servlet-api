package com.es.phoneshop.model.order;

import com.es.phoneshop.model.beans.UserBean;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.ArrayListProductDao;

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
