package com.es.phoneshop.model.order;

import com.es.phoneshop.model.product.ArrayListProductDao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayListOrderDao implements OrderDao{
    private static List<Order> orders = new ArrayList<>();

    private static volatile ArrayListOrderDao instance;

    public static ArrayListOrderDao getInstance() {
        ArrayListOrderDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ArrayListProductDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ArrayListOrderDao();
                }
            }
        }
        return localInstance;
    }
    @Override
    public Order getOrder(Long id) {
        return orders.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst().orElseThrow(() -> new NoSuchElementException());

    }

    @Override
    public void save(Order order) {
        boolean orderInList = orders.stream()
                .noneMatch(order::equals);
        if (orderInList) {
            orders.add(order);
        } else {
            //???
        }
    }
}
