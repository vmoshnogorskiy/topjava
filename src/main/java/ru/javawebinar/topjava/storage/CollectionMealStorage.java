package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class CollectionMealStorage implements MealStorage {

    private static final Logger log = getLogger(CollectionMealStorage.class);

    private static final AtomicInteger atomicCounter = new AtomicInteger();

    private final ConcurrentMap<Integer, Meal> storage = new ConcurrentHashMap<>();

    public CollectionMealStorage() {
        createStorageData();
    }

    @Override
    public Meal create(Meal meal) {
        log.info("Create meal");
        Integer newId = generateId();
        storage.put(newId, new Meal(newId, meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        return meal;
    }

    @Override
    public Meal get(int id) {
        log.info("Get meal by id {}", id);
        return storage.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        log.info("Updating meal {}", meal.getId());
        Meal oldMeal = storage.get(meal.getId());
        return storage.replace(meal.getId(), oldMeal, meal) ? meal : null;
    }

    @Override
    public void delete(int id) {
        log.info("Removing meal by id {}", id);
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        log.info("Get all stored meals");
        return new ArrayList<>(storage.values());
    }

    private void createStorageData() {
        for (Meal meal : MealsUtil.getMeals()) {
            create(meal);
        }
    }

    private Integer generateId() {
        return atomicCounter.incrementAndGet();
    }
}
