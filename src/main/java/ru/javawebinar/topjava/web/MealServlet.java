package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.filter.FilteredMealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        MealsUtil util = new MealsUtil(new FilteredMealTo());
        List<MealTo> mealsTo = util.filter.filteredByStreams(util.getMeals(),
                LocalTime.of(0, 0, 0, 0),
                LocalTime.of(23, 59, 59),
                MealsUtil.CALORIES_PER_DAY);
        request.setAttribute("mealsList", mealsTo);
        request.setAttribute("formatter", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}