package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCard(HttpSession session);

    void addToCart(Cart cart, Product product, Integer quantity);
    boolean delete(Cart cart, Product product);
    void updateCart(Cart cart, Product product, Integer quantity);
    void recalculateCart(Cart cart);
}