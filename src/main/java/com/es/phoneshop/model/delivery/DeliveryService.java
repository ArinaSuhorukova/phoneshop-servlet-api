package com.es.phoneshop.model.delivery;

import javafx.util.Pair;

import java.math.BigDecimal;
import java.util.List;

public interface DeliveryService {
    void addToDeliveryService(String name, BigDecimal cost);
    List<Pair<String, BigDecimal>> getDeliveryOptionsList();
}
