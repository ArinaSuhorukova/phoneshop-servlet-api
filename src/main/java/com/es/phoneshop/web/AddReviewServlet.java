package com.es.phoneshop.web;

import com.es.phoneshop.model.review.ReviewBean;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.review.ArrayListReviewDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

public class AddReviewServlet extends HttpServlet {
    private ArrayListReviewDao arrayListReviewDao;
    private ProductDao dao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws NoSuchElementException, ServletException, IOException {
        try {
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        } catch (NoSuchElementException ex) {
            //request.getRequestDispatcher("/WEB-INF/pages/404.jsp").forward(request, response);
            response.sendError(404);
        }
    }

    @Override
    public void init() throws ServletException, NoSuchElementException {
        super.init();
        arrayListReviewDao = ArrayListReviewDao.getInstance();
        dao = ArrayListProductDao.getInstance();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = loadProduct(request);
        ReviewBean reviewBean = new ReviewBean();
        reviewBean.setName(request.getParameter("name"));
        reviewBean.setComment(request.getParameter("comment"));
        reviewBean.setRating("rating");
        arrayListReviewDao.save(reviewBean);
        request.setAttribute("reviews", arrayListReviewDao.getReviews());
        response.sendRedirect("http://localhost:8080/phoneshop-servlet-api/products/" + product.getId());
    }

    private Product loadProduct(HttpServletRequest request) throws NoSuchElementException {
        String idString = request.getRequestURI().substring((request.getContextPath() + request.getServletPath()).length() + 1);
        Long id = Long.parseLong(idString);
        return dao.getProduct(id);
    }

}
