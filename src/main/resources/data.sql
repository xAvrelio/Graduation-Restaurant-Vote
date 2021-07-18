INSERT INTO users (id, email, password)
VALUES (1, 'user@yandex.ru', 'password'),
       (2, 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurants (id, name)
VALUES (1, 'La Plaza'),
       (2, 'Dedushka'),
       (3, 'Saccana');

INSERT INTO lunchs (id, name, price, DATE, restaurant_id)
values (1, 'Sandwich', 2.55, '2021-07-17', 1),
       (2, 'Some food', 4.99, '2021-07-17', 1),
       (3, 'Some drink', 1.25, '2021-07-17', 1),
       (4, 'Hamburger', 3.44, '2021-07-17', 2),
       (5, 'Pizza', 6.01, '2021-07-17', 2);

INSERT INTO votes (id, date, restaurant_id, user_id)
values (1,'2021-07-10', 1, 1),
       (2,'2021-07-10', 2, 2),
       (3,'2021-07-11', 1, 1),
       (4,'2021-07-12', 3, 2),
       (5,'2021-07-15', 1, 1),
       (6,'2021-07-17', 1, 2),
       (7,'2021-07-17', 1, 1);




