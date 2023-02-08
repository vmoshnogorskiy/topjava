package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.slf4j.LoggerFactory.getLogger;

public class MealStorageCollection implements MealStorage {

    private static final Logger log = getLogger(MealStorageCollection.class);

    protected final ConcurrentMap<Integer, Meal> storage = new ConcurrentHashMap<>();

    public void createStorageData() {
        for (Meal meal : MealsUtil.getMeals()) {
            storage.put(meal.getId(), meal);
        }
    }

    @Override
    public Meal create(Meal meal) {
        log.info("Saving meal " + meal.getId());
        storage.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal get(int id) {
        log.info("Get meal by id " + id);
        return storage.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        log.info("Updating meal " + meal.getId());
        storage.put(meal.getId(), new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        return meal;
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
}
