package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.repository.jpa.JpaMealRepository;

@ActiveProfiles(profiles = {"postgres","jpa"})
public class JpaMealServiceTest extends AbstractMealServiceTest {
    public JpaMealServiceTest() {
        super(new MealService(new JpaMealRepository()));
    }
}
