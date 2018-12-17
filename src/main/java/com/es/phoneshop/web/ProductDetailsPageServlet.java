package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.CartServiceImpl;
import com.es.phoneshop.model.product.cart.ViewedProductsService;
import com.es.phoneshop.model.product.cart.ViewedProductsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class ProductDetailsPageServlet extends HttpServlet {
    private ArrayListProductDao dao;
    private CartService cartService;
    private ViewedProductsService viewedProductsService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws NoSuchElementException, ServletException, IOException {
        try {
            Product product = loadProduct(request);
            viewedProductsService.cleanViewedProducts(product);
            request.setAttribute("product", product);
            request.setAttribute("viewedProducts", viewedProductsService.getList());
            request.setAttribute("cart", cartService.getCard(request.getSession()));
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
            viewedProductsService.addToViewedProducts(product);
        } catch (NoSuchElementException ex) {
            //request.getRequestDispatcher("/WEB-INF/pages/404.jsp").forward(request, response);
            response.sendError(404);
        }
    }

    @Override
    public void init() throws ServletException, NoSuchElementException {
        super.init();
        dao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();
        viewedProductsService = new ViewedProductsServiceImpl();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = loadProduct(request);
        request.setAttribute("product", product);

        Integer quantity = null;
        try {
            String quantityString = request.getParameter("quantity");
            quantity = Integer.valueOf(quantityString);

        } catch (NumberFormatException ex) {
            request.setAttribute("quantityError", "Not a number");

        }
        if (quantity != null) {
            try {
                cartService.addToCart(cartService.getCard(request.getSession()), product, quantity);
                response.sendRedirect(request.getRequestURI() + "?message=Product added successfully");
            } catch (NoSuchElementException ex) {
                request.setAttribute("quantityError", "Not enough stock");
                request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        }
    }

    private Product loadProduct(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String stringId = url.substring(url.lastIndexOf("/") + 1);
        Long id = Long.parseLong(stringId);
        return dao.getProduct(id);
    }
}
