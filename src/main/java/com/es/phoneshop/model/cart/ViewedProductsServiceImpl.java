package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ViewedProductsServiceImpl implements ViewedProductsService {
    private List<Product> viewedProducts = new ArrayList<>();

    @Override
    public void addToViewedProducts(Product product){
        if (viewedProducts.size() == 3) {
            viewedProducts.remove(0);
        }
        viewedProducts.add(product);
    }

    @Override
    public void cleanViewedProducts(Product product){
        Optional<Product> currentProduct = viewedProducts.stream().filter(x-> (x.getCode().equals(product.getCode()))).findFirst();
        if(currentProduct.isPresent()) {
            viewedProducts.remove(currentProduct.get());
        }
    }

    @Override
    public List<Product> getList() {
        return viewedProducts;
    }

}
