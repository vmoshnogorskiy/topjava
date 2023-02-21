package ru.javawebinar.topjava;

import org.junit.Assert;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_MEAL_ID_1 = START_SEQ + 3;
    public static final int USER_MEAL_ID_2 = START_SEQ + 4;
    public static final int USER_MEAL_ID_3 = START_SEQ + 5;
    public static final int USER_MEAL_ID_4 = START_SEQ + 6;
    public static final int USER_MEAL_ID_5 = START_SEQ + 7;
    public static final int USER_MEAL_ID_6 = START_SEQ + 8;
    public static final int NOT_FOUND = 10;

    public static final Meal user_meal_1 = new Meal(USER_MEAL_ID_1,
            LocalDateTime.of(2023, Month.JANUARY, 30, 7, 10), "Завтрак 1", 1200);
    public static final Meal user_meal_2 = new Meal(USER_MEAL_ID_2,
            LocalDateTime.of(2023, Month.JANUARY, 30, 9, 50), "Завтрак 2", 450);
    public static final Meal user_meal_3 = new Meal(USER_MEAL_ID_3,
            LocalDateTime.of(2023, Month.JANUARY, 30, 14, 10), "Обед", 750);
    public static final Meal user_meal_4 = new Meal(USER_MEAL_ID_4,
            LocalDateTime.of(2023, Month.JANUARY, 30, 19, 15), "Ужин", 550);
    public static final Meal user_meal_5 = new Meal(USER_MEAL_ID_5,
            LocalDateTime.of(2023, Month.FEBRUARY, 3, 7, 10), "Завтрак 1", 800);
    public static final Meal user_meal_6 = new Meal(USER_MEAL_ID_6,
            LocalDateTime.of(2023, Month.FEBRUARY, 3, 9, 50), "Завтрак 2", 250);
    public static final Meal duplicate_meal = new Meal(null,
            LocalDateTime.of(2023, Month.JANUARY, 30, 9, 50), "перекус", 180);

    public static void assertMatch(Meal actual, Meal expected) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getDateTime(), actual.getDateTime());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.getCalories(), actual.getCalories());
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields().isEqualTo(expected);
    }

    public static Meal getUpdated(Meal m) {
        Meal updated = new Meal(m);
        updated.setDateTime(LocalDateTime.of(2023, Month.JANUARY, 30, 18, 50));
        updated.setDescription("updated.. ужин");
        updated.setCalories(300);
        return updated;
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2023, Month.JANUARY, 30, 18, 50), "new meal", 200);
    }
}
