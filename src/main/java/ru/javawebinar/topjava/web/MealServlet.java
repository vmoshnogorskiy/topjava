package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.storage.CollectionMealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private MealStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new CollectionMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            log.debug("redirect to meals");
            request.setAttribute("mealsList", MealsUtil.filteredByStreams(storage.getAll(),
                    LocalTime.MIN,
                    LocalTime.MAX,
                    MealsUtil.CALORIES_PER_DAY));
            request.setAttribute("formatter", FORMATTER);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        Meal m;
        switch (action) {
            case "delete":
                storage.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("meals");
                return;
            case "add":
                m = MealsUtil.empty;
                m.setDateTime(LocalDateTime.now());
                break;
            case "edit":
                m = storage.get(Integer.parseInt(request.getParameter("id")));
                break;
            default:
                response.sendRedirect("meals");
                return;
        }
        request.setAttribute("meal", m);
        request.setAttribute("formatter", FORMATTER);
        request.getRequestDispatcher("/edit_meal.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        final boolean isCreate = (id == null || id.length() == 0);

        if (isCreate) {
            storage.create(new Meal(null, dateTime, description, calories));
        } else {
            storage.update(new Meal(Integer.parseInt(id), dateTime, description, calories));
        }
        response.sendRedirect("meals");
    }
}