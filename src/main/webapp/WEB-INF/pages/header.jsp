
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <a href="<c:url value="/cart"/> ">
        <span>Cart: ${cart.totalPrice}</span>
    </a>
</header>

