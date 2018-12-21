package com.es.phoneshop.web;

import com.es.phoneshop.model.UserBean;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckoutPageServlet extends HttpServlet {

    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init(){
        cartService = CartServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
         request.setAttribute("cart", cartService.getCard(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }
    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Cart cart = cartService.getCard(request.getSession());
        UserBean userBean = new UserBean();
        userBean.setName(request.getParameter("name"));
        userBean.setDeliveryAddress(request.getParameter("deliveryAddress"));
        userBean.setPhone(request.getParameter("phone"));

        if(userBean.getDeliveryAddress() != "" && userBean.getName() != "" && userBean.getPhone() != "") {
            Order order = orderService.placeOrder(cart, userBean);
            cartService.clearCart(cart);
            response.sendRedirect(request.getContextPath() + "/orderOverview/" + order.getId());
        }
    }
}
