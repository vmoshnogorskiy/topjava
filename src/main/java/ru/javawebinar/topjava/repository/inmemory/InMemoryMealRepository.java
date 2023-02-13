package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private static final Comparator<Meal> MEAL_COMPARATOR = Comparator.comparing(Meal::getDateTime);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> oldMeal.getUserId().equals(userId) ? meal : oldMeal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        Meal storedMeal = repository.get(id);
        return userId.equals(storedMeal.getUserId()) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        Meal storedMeal = repository.get(id);
        return storedMeal != null && storedMeal.getUserId().equals(userId) ? storedMeal : null;
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return repository.values().stream()
                .filter(meal -> userId.equals(meal.getUserId()))
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }
}

