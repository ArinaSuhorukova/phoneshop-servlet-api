package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ViewedProductsService {
    void addToViewedProducts(Product product);
    void cleanViewedProducts(Product product);
    List<Product> getList();
}
