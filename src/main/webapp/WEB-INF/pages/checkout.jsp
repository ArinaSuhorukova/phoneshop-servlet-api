<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Checkout">
    <jsp:useBean id="cart" type="com.es.phoneshop.model.product.cart.Cart" scope="request"/>
    <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
        <p>
            <button>Place order</button>
        </p>
        <c:if test="${not empty quantityErrors}">
            <p class="error">Failed to update</p>
        </c:if>
        <c:if test="${not empty param.deletionError}">
            <p class="error">Failed to delete</p>
        </c:if>
        <c:if test="${not empty param.message}">
            <p class="success">${param.message}</p>
        </c:if>

        <table>
            <tr>
                <td></td>
                <td>Code</td>
                <td>Description</td>
                <td class="number">Price</td>
                <td class="number">Quantity</td>
                <td></td>
            </tr>
            <c:forEach var="item" items="${cart.cartItems}">
                <tr>
                    <td>
                        <a href="<c:url value="/products/${item.product.id}"/> ">
                            <img class="product-title"
                                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ex..${item.product.imageUrl}">
                        </a>
                    </td>
                    <td>
                        <a href="<c:url value="/products/${item.product.id}"/> ">
                                ${item.product.description}
                        </a>
                    </td>

                    <td>
                        <fmt:formatNumber value="${item.product.price}" type="currency"
                                          currencySymbol="${item.product.currency.symbol}"/></td>
                    <td>
                            ${item.quantity}
                    </td>

                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td>Total:</td>
                <td>${cart.totalPrice}</td>
            </tr>
        </table>
        <br>
        <input name="name" placeholder="name">
        <br><br>
        <input name="deliveryAddress" placeholder="deliveryAddress">
        <br><br>
        <input name="phone" placeholder="phone">

        <button>Place order</button>
    </form>
</tags:master>