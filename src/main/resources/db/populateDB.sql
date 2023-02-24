DELETE FROM meals;
DELETE FROM user_role;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2023-01-30 07:10:00','Завтрак 1', 1200, 100000),
       ('2023-01-30 09:50:00', 'Завтрак 2', 450, 100000),
       ('2023-01-30 14:10:00', 'Обед', 750, 100000),
       ('2023-01-30 19:15:00', 'Ужин', 550, 100000),

       ('2023-01-31 00:00:00', 'Поздний ужин', 550, 100000),

       ('2023-02-03 07:10:00','Завтрак 1', 800, 100000),
       ('2023-02-03 09:50:00', 'Завтрак 2', 250, 100000),

       ('2023-01-30 07:10:00','Завтрак 1', 800, 100001),
       ('2023-01-30 09:50:00', 'Завтрак 2', 470, 100001);

