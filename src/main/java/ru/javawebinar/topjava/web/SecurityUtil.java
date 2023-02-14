package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Component;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

@Component
public class SecurityUtil {
    private static int userId = 1;

    public static int authUserId() {
        return userId;
    }

    public void setAuthUserId(int userId) {
        SecurityUtil.userId = userId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}