<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <br><br>
    <form method="get" action="meals">
        <input type="hidden" name="action" value="dateTimeFilter">
        <dl>
            <dt>DateFrom:</dt>
            <dd>
                <label>
                    <input type="date" name="dateFrom" required>
                </label>
            </dd>
        </dl>
        <dl>
            <dt>DateTo:</dt>
            <dd>
                <label>
                    <input type="date" name="dateTo" required>
                </label>
            </dd>
        </dl>
        <dl>
            <dt>TimeFrom:</dt>
            <dd>
                <label>
                    <input type="time" name="timeFrom" required>
                </label>
            </dd>
        </dl>
        <dl>
            <dt>TimeTo:</dt>
            <dd>
                <label>
                    <input type="time" name="timeTo" required>
                </label>
            </dd>
        </dl>
        <button type="submit">Execute Date Filter</button>
    </form>
    <a href="meals?action=create">Add Meal</a>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>