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

public class MealStorageCollection implements MealStorage {

    private static final Logger log = getLogger(MealStorageCollection.class);

    private static final AtomicInteger atomicCounter = new AtomicInteger();

    protected final ConcurrentMap<Integer, Meal> storage = new ConcurrentHashMap<>();

    public MealStorageCollection() {
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
        log.info("Get meal by id " + id);
        return storage.get(id);
    }

    @Override
    public Meal update(Integer id, Meal meal) {
        log.info("Updating meal " + meal.getId());
        Meal oldMeal = storage.get(id);
        if (oldMeal != null) {
            oldMeal.setDateTime(meal.getDateTime());
            oldMeal.setDescription(meal.getDescription());
            oldMeal.setCalories(meal.getCalories());
            storage.put(id, oldMeal);
            return oldMeal;
        }
        return null;
    }

    @Override
    public void delete(int id) {
        log.info("Removing meal by id " + id);
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        log.info("Get all stored meals");
        return new ArrayList<>(storage.values());
    }

    private void createStorageData() {
        for (Meal meal : MealsUtil.getMeals()) {
            storage.put(meal.getId(), meal);
        }
    }

    @Override
    public Integer generateId() {
        return atomicCounter.incrementAndGet();
    }
}
