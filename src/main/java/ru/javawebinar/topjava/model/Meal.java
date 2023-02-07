package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Meal {

    private Integer id;
    private LocalDateTime dateTime;

    private String description;

    private int calories;

    private static final AtomicInteger atomicCounter = new AtomicInteger();

    public static final Meal EMPTY = new Meal();

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.id = generateId();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    private static Integer generateId() {
        return atomicCounter.incrementAndGet();

    }
}