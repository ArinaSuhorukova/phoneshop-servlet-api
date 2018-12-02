package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCard();
    void addToCart(Product product, Integer quantity);
}
