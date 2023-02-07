<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 07.02.2023
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
    <title>Edit meal ${meal.description}</title>
</head>
<body>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.id}">
        DateTime: <input type="datetime-local" name="datetime" value="<c:out value="${meal.dateTime}" />"/> <br/>
        Description: <input type="text" name="description" value="<c:out value="${meal.description}" />"/> <br/>
        Calories: <input type="text" name="calories" value="<c:out value="${meal.calories}" />"/> <br/>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>

</body>
</html>
