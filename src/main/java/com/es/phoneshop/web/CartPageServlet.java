package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class CartPageServlet extends HttpServlet  {
    private  ArrayListProductDao dao;
    private CartService cartService;
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws NoSuchElementException, ServletException, IOException {

            request.setAttribute("cart", cartService.getCard(request.getSession()));
            request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);

    }

    @Override
    public void init() throws ServletException, NoSuchElementException{
        super.init();
        dao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();


    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        Cart cart = cartService.getCard(request.getSession());
        Map <Long, String> quantityErrors = new HashMap<>();
        for(int i = 0; i < productIds.length; i++){
            Long productId = Long.valueOf(productIds[i]);
            Product product = dao.getProduct(productId);
            Integer quantity = null;
            try{
                quantity = Integer.valueOf(quantities[i]);
            }
            catch (NumberFormatException ex){
                quantityErrors.put(product.getId(),"not a number");
                 }
            if(quantity != null){
                try{
                    cartService.updateCart(cart, product, quantity);
                }
                catch (NoSuchElementException ex){
                    quantityErrors.put(product.getId(),"Not enough stock");
                }
            }
        }

        request.setAttribute("quantityErrors", quantityErrors);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request,response);
    }

}
