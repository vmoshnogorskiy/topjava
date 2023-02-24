package ru.javawebinar.topjava;

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
    public static final int USER_MEAL_ID_7 = START_SEQ + 9;
    public static final int ADMIN_MEAL_ID_1 = START_SEQ + 10;
    public static final int ADMIN_MEAL_ID_2 = START_SEQ + 11;
    public static final int NOT_FOUND = 10;

    public static final Meal userMeal1 = new Meal(USER_MEAL_ID_1,
            LocalDateTime.of(2023, Month.JANUARY, 30, 7, 10), "Завтрак 1", 1200);
    public static final Meal userMeal2 = new Meal(USER_MEAL_ID_2,
            LocalDateTime.of(2023, Month.JANUARY, 30, 9, 50), "Завтрак 2", 450);
    public static final Meal userMeal3 = new Meal(USER_MEAL_ID_3,
            LocalDateTime.of(2023, Month.JANUARY, 30, 14, 10), "Обед", 750);
    public static final Meal userMeal4 = new Meal(USER_MEAL_ID_4,
            LocalDateTime.of(2023, Month.JANUARY, 30, 19, 15), "Ужин", 550);
    public static final Meal userMeal5 = new Meal(USER_MEAL_ID_5,
            LocalDateTime.of(2023, Month.JANUARY, 31, 0, 0), "Поздний ужин", 550);
    public static final Meal userMeal6 = new Meal(USER_MEAL_ID_6,
            LocalDateTime.of(2023, Month.FEBRUARY, 3, 7, 10), "Завтрак 1", 800);
    public static final Meal userMeal7 = new Meal(USER_MEAL_ID_7,
            LocalDateTime.of(2023, Month.FEBRUARY, 3, 9, 50), "Завтрак 2", 250);
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL_ID_1,
            LocalDateTime.of(2023, Month.JANUARY, 30, 7, 10), "Завтрак 1", 800);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL_ID_2,
            LocalDateTime.of(2023, Month.JANUARY, 30, 9, 50), "Завтрак 2", 470);
    public static final Meal duplicateMeal = new Meal(null, userMeal2.getDateTime(), "перекус", 180);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
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
