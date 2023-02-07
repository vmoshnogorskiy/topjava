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
    <a href="meals?action=add">Add Meal </a>
    <br>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>
        <jsp:useBean id="mealsList" scope="request" type="java.util.List"/>
        <c:forEach items="${mealsList}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <c:choose>
                <c:when test="${meal.excess}"> <tr style="color: red"> </c:when>
                <c:otherwise><tr style="color: green"></c:otherwise>
            </c:choose>
            <td>${meal.dateTime.format(formatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?id=${meal.id}&action=delete">Delete</a></td>
            <td><a href="meals?id=${meal.id}&action=edit">Edit</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
