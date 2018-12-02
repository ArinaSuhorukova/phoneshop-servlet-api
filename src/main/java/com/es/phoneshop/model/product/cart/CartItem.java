package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.Product;

public class CartItem {
    private Product product;
    private int quantity;

    @Override
    public String toString() {
        return "CartItem[" + product.getCode() +
                ", " + quantity +
                ']';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartItem(Product product, int quantity) {

        this.product = product;
        this.quantity = quantity;
    }
}
