package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        //System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        Map<LocalDate, Integer> caloriesPerDate = new HashMap<>();
        List<UserMealWithExcess> excessList = new ArrayList<>();

        for (UserMeal meal : meals) {
            System.out.println(meal.getDateTime().toLocalDate());
            if (caloriesPerDate.containsKey(meal.getDateTime().toLocalDate())) {
                caloriesPerDate.put(meal.getDateTime().toLocalDate(), meal.getCalories() + caloriesPerDate.get(meal.getDateTime().toLocalDate()));
            } else {
                caloriesPerDate.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            }
        }

        for (Map.Entry<LocalDate, Integer> entry : caloriesPerDate.entrySet()) {
            for (UserMeal meal : meals) {
                if(isMeetDateTime(entry, meal, startTime, endTime)) {
                    excessList.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                            entry.getValue() > caloriesPerDay));
                }
            }
        }
        return excessList;
    }

    private static boolean isMeetDateTime(Map.Entry<LocalDate, Integer> entry, UserMeal meal, LocalTime startTime, LocalTime endTime) {
        return entry.getKey().isEqual(meal.getDateTime().toLocalDate())
                && isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime);
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
