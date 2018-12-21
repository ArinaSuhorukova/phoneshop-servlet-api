<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Checkout">
    <jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
    <form method="post" action="${pageContext.servletContext.contextPath}/orderOverview">
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
        <input name="name" placeholder="name">
        <br><br>
        <input name="deliveryAddress" placeholder="deliveryAddress">
        <br><br>
        <input name="phone" placeholder="phone">
<br><br>
        <table>
            <tr>
                <td>Delivery method</td>
                <td>Cost</td>
            </tr>
            <c:forEach var="item" items="${deliveryOptions}">
            <tr>
                <td>${item.key}</td>
                <td>${item.value}</td>
            </tr>
            </c:forEach>
        </table>
        <br><br>
        <select>
            <c:forEach var="item" items="${deliveryOptions}">
                <option>${item.key}</option>
            </c:forEach>
        </select>
        <button>Place order</button>
        <br><br>
    </form>
</tags:master>