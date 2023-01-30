package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMealWithExcess {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMealWithExcess that = (UserMealWithExcess) o;

        if (calories != that.calories) return false;
        if (excess != that.excess) return false;
        if (!dateTime.equals(that.dateTime)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = dateTime.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + calories;
        result = 31 * result + (excess ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}
