package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private static CartService cartService = new CartServiceImpl();
    private Cart cart = new Cart();

    private CartServiceImpl(){}

    public static CartService getInstance(){
        return cartService;
    }

    @Override
    public Cart getCard() {
        return cart;
    }

    @Override
    public void addToCart(Product product, Integer quantity) {

        Optional<CartItem> cartItemOptional = cart.getCartItems().stream().filter(cartItem -> product.getId().equals(cartItem.getProduct().getId())).findAny();
        if(cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        else {
            cart.getCartItems().add(new CartItem(product,quantity));
        }
    }
}
