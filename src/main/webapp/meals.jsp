<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        <jsp:useBean id="mealsList" scope="request" type="java.util.List"/>
        <c:forEach items="${mealsList}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <c:set var="excess" value="${meal.excess}"/>
            <c:choose>
                <c:when test="${excess == true}">
                    <tr style="color: red">
                        <td>${meal.stringDateTime}</td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                    </tr>
                </c:when>
                <c:when test="${excess == false}">
                    <tr style="color: green">
                        <td>${meal.stringDateTime}</td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                    </tr>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
</section>
</body>
</html>
