package com.es.phoneshop.web;

import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckoutPageServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init(){
        cartService = CartServiceImpl.getInstance();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
         request.setAttribute("cart", cartService.getCard(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }
}