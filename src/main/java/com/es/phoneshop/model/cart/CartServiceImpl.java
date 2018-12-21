package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private static final String CART_ATTRIBUTE = "cart";
    private static CartService cartService = new CartServiceImpl();

    private CartServiceImpl(){}

    public static CartService getInstance(){
        return cartService;
    }

    @Override
    public Cart getCard(HttpSession session) {
        Cart cart = (Cart)session.getAttribute(CART_ATTRIBUTE);
        if(cart == null){
            cart = new Cart();
            session.setAttribute(CART_ATTRIBUTE,cart);
        }
        return cart;
    }

    @Override
    public void updateCart(Cart cart, Product product, Integer quantity) {

        if(product.getStock() < quantity){
            throw new NoSuchElementException();
        }

        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(cartItem -> product.getId().equals(cartItem.getProduct().getId()))
                .findAny();
        if(cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(quantity);
            product.setStock(product.getStock() - quantity);
        }
        else {
            throw new NoSuchElementException();
        }
        recalculateCart(cart);
    }

    @Override
    public boolean delete(Cart cart, Product product){
        boolean result = cart.getCartItems().removeIf(cartItem -> product.equals(cartItem.getProduct()));
        recalculateCart(cart);
        return result;
    }
    @Override
    public void addToCart(Cart cart, Product product, Integer quantity) {
        if(quantity > product.getStock()) {
          throw new NoSuchElementException();
        }
        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(cartItem -> product.getId().equals(cartItem.getProduct().getId()))
                .findAny();
        if(cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            product.setStock(product.getStock() - quantity);
        }
        else {
            cart.getCartItems().add(new CartItem(product, quantity));
            product.setStock(product.getStock() - quantity);
        }
        recalculateCart(cart);
    }

    @Override
    public void recalculateCart(Cart cart){
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, (x,y) -> x.add(y));
        cart.setTotalPrice(totalPrice);
    }

    @Override
    public void clearCart(Cart cart) {
        cart.getCartItems().clear();
        recalculateCart(cart);
    }
}
