package com.es.phoneshop.model.delivery;

import javafx.util.Pair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DeliveryServiceImpl implements DeliveryService {
    List<Pair<String,BigDecimal>> deliveryOptions = new ArrayList<>();
    @Override
    public void addToDeliveryService(String name, BigDecimal cost) {
        deliveryOptions.add(new Pair<>(name,cost));
    }

    @Override
    public List<Pair<String, BigDecimal>> getDeliveryOptionsList() {
        return deliveryOptions;
    }
}
