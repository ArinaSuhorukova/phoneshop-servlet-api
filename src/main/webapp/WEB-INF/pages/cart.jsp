<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Cart">
    <jsp:useBean id="cart" type="com.es.phoneshop.model.product.cart.Cart" scope="request"/>

    <c:if test="${not empty param.message}">
        <p class="success">${param.message}</p>
    </c:if>
    <form method="post" action="${pageContext.servletContext.contextPath}/cart">
        <p>
            <button>Update cart</button>
        </p>
        <c:if test="${not empty quantityErrors}">
            <p class="error">Failed to update cart</p>
        </c:if>
        <table>
            <tr>
                <td></td>
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

                    <td>${item.product.description}</td>
                    <td><fmt:formatNumber value="${item.product.price}" type="currency"
                                          currencySymbol="${item.product.currency.symbol}"/></td>
                    <td>
                        <input name="quantity"
                               value="${not empty quantityErrors[item.product.id] ? paramValues['quantity'][status.index] : item.quantity}"
                               class="number" />
                        <input type="hidden" name="productId" value="${item.product.id}">
                        <c:if test="${not empty quantityErrors[item.product.id]}">
                            <p class="error">${quantityErrors[item.product.id]}</p>
                        </c:if>
                    </td>
                    <td>
                        <button formaction="${pageContext.servletContext.contextPath}/cart/${item.product.id}">Delete
                        </button>
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
        <button>Update</button>
        <a href="<c:url value="/checkout"/> ">Checkout</a>
    </form>
</tags:master>