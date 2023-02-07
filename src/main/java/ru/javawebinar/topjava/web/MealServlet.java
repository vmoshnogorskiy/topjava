package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MapStorage storage;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MapStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            log.debug("redirect to meals");
            request.setAttribute("mealsList", storage.getAll());
            request.setAttribute("formatter", formatter);
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
                m = Meal.EMPTY;
                break;
            case "edit":
                m = storage.get(Integer.parseInt(request.getParameter("id")));
                break;
            default:
                throw new IllegalArgumentException("Action" + action + "is illegal");
        }
        request.setAttribute("meal", m);
        request.setAttribute("formatter", formatter);
        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"));
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        final boolean isCreate = (id == null || id.length() == 0);

        Meal m;
        if (isCreate) {
            m = new Meal(dateTime, description, Integer.parseInt(calories));
            storage.create(m);
        } else {
            m = storage.get(Integer.parseInt(id));
            m.setDateTime(dateTime);
            m.setDescription(description);
            m.setCalories(Integer.parseInt(calories));
            storage.update(m);
        }
        response.sendRedirect("meals");
    }
}