package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class ProductDetailsPageServlet extends HttpServlet  {
    private  ArrayListProductDao dao;
    private CartService cartService;
    private List<Product> viewedProducts = new ArrayList<>();

    private void addToViewedProducts(Product product){
        if (viewedProducts.size() == 3) {
            viewedProducts.remove(0);
        }
        viewedProducts.add(product);

    }
    private void cleanViewedProducts(Product product){
        Optional<Product> currentProduct = viewedProducts.stream().filter(x-> (x.getCode().equals(product.getCode()))).findFirst();
        if(currentProduct.isPresent()) {
            viewedProducts.remove(currentProduct.get());
        }
    }
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws NoSuchElementException, ServletException, IOException {
        try{
            Product product = loadProduct(request);
            cleanViewedProducts(product);
            request.setAttribute("product", product);
            request.setAttribute("viewedProducts", viewedProducts);
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
            addToViewedProducts(product);
        }
        catch (NoSuchElementException ex){
            //request.getRequestDispatcher("/WEB-INF/pages/404.jsp").forward(request, response);
            response.sendError(404);
        }
       }

    @Override
    public void init() throws ServletException, NoSuchElementException{
        super.init();
        dao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = loadProduct(request);

        request.setAttribute("product", product);

        Integer quantity = null;
        try{
            String quantityString = request.getParameter("quantity");
            quantity = Integer.valueOf(quantityString);

        }catch (NumberFormatException ex){
            request.setAttribute("quantityError", "Not a number");

        }
        if(quantity != null) {
            cartService.addToCart(product, quantity);
            response.sendRedirect(request.getRequestURI()+"?message=Product added successfully");
        }
        else {
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        }
    }
    private Product loadProduct(HttpServletRequest request){
        StringBuffer url = request.getRequestURL();
        String stringId = url.substring(url.lastIndexOf("/") + 1);
        Long id = Long.parseLong(stringId);
        return dao.getProduct(id);
    }
}
