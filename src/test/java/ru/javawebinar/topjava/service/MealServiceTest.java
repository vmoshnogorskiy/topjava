package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_ID_1, user.getId());
        assertMatch(meal, user_meal_1);
    }

    @Test
    public void getNotOwnerMeal() {
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID_1, admin.getId()));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID_1, user.getId());
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID_1, user.getId()));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.NOT_FOUND, user.getId()));
    }

    @Test
    public void deleteNotOwnerMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_ID_1, admin.getId()));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> allFiltered = service.getBetweenInclusive(
                LocalDate.of(2023, 01, 30),
                LocalDate.of(2023, 01, 30),
                user.getId());
        MealTestData.assertMatch(allFiltered, user_meal_4, user_meal_3, user_meal_2, user_meal_1);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(user.getId());
        MealTestData.assertMatch(all, user_meal_6, user_meal_5, user_meal_4, user_meal_3, user_meal_2, user_meal_1);
    }

    @Test
    public void update() {
        Meal updated = getUpdated(user_meal_4);
        service.update(updated, user.getId());
        assertMatch(service.get(user_meal_4.getId(), user.getId()), getUpdated(user_meal_4));
    }

    @Test
    public void updateNotOwnerMeal() {
        Meal updated = getUpdated(user_meal_3);
        assertThrows(NotFoundException.class, () -> service.update(updated, admin.getId()));
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), user.getId());
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, user.getId()), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> service.create(duplicate_meal, user.getId()));
    }
}