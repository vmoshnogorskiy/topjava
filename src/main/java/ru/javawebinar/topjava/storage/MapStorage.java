package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.slf4j.LoggerFactory.getLogger;

public class MapStorage implements Storage {

    protected final ConcurrentMap<Integer, Meal> storage = new ConcurrentHashMap<>();

    private static final Logger log = getLogger(MapStorage.class);

    {
        for (Meal meal : MealsUtil.getMeals()) {
            storage.put(meal.getId(), meal);
        }
    }

    @Override
    public void create(Meal meal) {
        log.info("Saving meal " + meal.getId());
        storage.put(meal.getId(), meal);
    }

    @Override
    public Meal get(Integer id) {
        log.info("Get meal by id " + id);
        return storage.get(id);
    }

    @Override
    public void update(Meal meal) {
        log.info("Updating meal " + meal.getId());
        storage.put(meal.getId(), new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories()));
    }

    @Override
    public void delete(Integer id) {
        log.info("Removing meal by id " + id);
        storage.remove(id);
    }

    @Override
    public List<MealTo> getAll() {
        log.info("Get all stored meals");
        return MealsUtil.filter.filteredByStreams(new ArrayList<>(storage.values()),
                LocalTime.of(0, 0, 0, 0),
                LocalTime.of(23, 59, 59),
                MealsUtil.CALORIES_PER_DAY);
    }
}
