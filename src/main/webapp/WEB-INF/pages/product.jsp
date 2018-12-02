<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="tags" uri="urn:jsptagdir:/WEB-INF/tags" %>--%>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<html>
<head>
    <title>Product List</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
<header>
    <a href="${pageContext.servletContext.contextPath}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
        PhoneShop
    </a>
</header>
<main>
    cart: ${cart}
    <c:if test="${not empty param.message}">
        <p class="success">${param.message}</p>
    </c:if>
    <table>
        <tr>
            <td>
                <img class="product-title" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>
                <h1>${product.description}</h1>
                <p>Code: ${product.code}</p>
                <p>Stock: ${product.stock}</p>
                <p>Price: <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"></fmt:formatNumber> </p>
                <form method="post" action="${pageContext.servletContext.contextPath}/products/${product.id}">
                    Quantity: <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}" class="number">
                    <button>Add</button>
                    <c:if test="${not empty quantityError}">
                        <p class="error">${quantityError}</p>
                    </c:if>
                </form>
            </td>
        </tr>

    </table>
    <p>
        Viewed products
    </p>
    <table border="1">
    <tr>
        <c:forEach var="viewedProduct" items="${viewedProducts}">
        <td>
            <img class="product-title" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${viewedProduct.imageUrl}">
            <p><a href="${pageContext.servletContext.contextPath}/products/${viewedProduct.id}">${viewedProduct.description}</a></p>
            <p>Price: <fmt:formatNumber value="${viewedProduct.price}" type="currency" currencySymbol="${product.currency.symbol}"></fmt:formatNumber> </p>
        </td>
        </c:forEach>
    </tr>
    </table>


</main>
</body>
</html>