package com.es.phoneshop.web;

import com.es.phoneshop.model.beans.UserBean;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.delivery.DeliveryService;
import com.es.phoneshop.model.delivery.DeliveryServiceImpl;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.OrderServiceImpl;
import javafx.util.Pair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class CheckoutPageServlet extends HttpServlet {

    private CartService cartService;
    private OrderService orderService;
    private DeliveryService deliveryService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = CartServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
        deliveryService = new DeliveryServiceImpl();
        deliveryService.addToDeliveryService("Pick up from the store", new BigDecimal(0));
        deliveryService.addToDeliveryService("Mail delivery", new BigDecimal(50));
        deliveryService.addToDeliveryService("Courier delivery", new BigDecimal(100));
        Pair<Integer,Integer> pair = new Pair<>(1,1);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
         request.setAttribute("cart", cartService.getCard(request.getSession()));
        request.setAttribute("deliveryOptions", deliveryService.getDeliveryOptionsList());
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
