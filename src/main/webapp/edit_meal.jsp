<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>
<html>
<head>
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
    <title> ${meal.id == null ? 'Add meal' : 'Edit meal'} </title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<section>
    <h2>${meal.id == null ? 'Add meal' : 'Edit meal'}</h2>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.id}">
        DateTime: <input type="datetime-local" name="datetime" value="${meal.dateTime.format(formatter)}"/> <br/>
        Description: <input type="text" name="description" value="${meal.description}"/> <br/>
        Calories: <input type="number" name="calories" value="${meal.calories}"/> <br/>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>

</body>
</html>
