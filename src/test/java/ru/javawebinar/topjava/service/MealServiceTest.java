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
import static ru.javawebinar.topjava.MealTestData.NOT_FOUND;
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
        Meal meal = service.get(USER_MEAL_ID_1, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNotExist() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotOwnerMeal() {
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID_1, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID_1, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID_1, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotOwnerMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_ID_1, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> allFiltered = service.getBetweenInclusive(
                LocalDate.of(2023, 1, 30),
                LocalDate.of(2023, 1, 30),
                USER_ID);
        MealTestData.assertMatch(allFiltered, userMeal4, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void getBetweenInclusiveWithoutRestrictions() {
        List<Meal> allFiltered = service.getBetweenInclusive(
                null,
                null,
                ADMIN_ID);
        MealTestData.assertMatch(allFiltered, adminMeal2, adminMeal1);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        MealTestData.assertMatch(all, userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void update() {
        Meal updated = getUpdated(userMeal4);
        service.update(updated, USER_ID);
        assertMatch(service.get(userMeal4.getId(), USER_ID), getUpdated(userMeal4));
    }

    @Test
    public void updateNotOwnerMeal() {
        Meal updated = getUpdated(userMeal3);
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> service.create(duplicateMeal, USER_ID));
    }
}