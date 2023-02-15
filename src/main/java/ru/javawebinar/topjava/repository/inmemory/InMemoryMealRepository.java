package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private static final Comparator<Meal> MEAL_COMPARATOR = Comparator.comparing(Meal::getDateTime).reversed();
    private final Map<Integer,Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUserId(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(userId, meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(userId, (id, oldMeal) -> oldMeal.getUserId() == userId ? meal : oldMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal storedMeal = repository.get(id);
        return userId == storedMeal.getUserId() && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal storedMeal = repository.get(id);
        return storedMeal != null && storedMeal.getUserId().equals(userId) ? storedMeal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> userId == meal.getUserId())
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }
}

