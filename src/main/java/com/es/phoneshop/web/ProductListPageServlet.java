package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

public class ProductListPageServlet extends HttpServlet {
    private ArrayListProductDao dao;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoSuchElementException {
        request.setAttribute("products", dao.findProducts(
                request.getParameter("query"),
                request.getParameter("sort"),
                request.getParameter("order")
                ));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    @Override
    public void init() throws ServletException, NoSuchElementException{
        super.init();
        dao = ArrayListProductDao.getInstance();

    }
}