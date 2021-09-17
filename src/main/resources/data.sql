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

INSERT INTO menus (id, create_date, restaurant_id )
VALUES (1, '2021-07-11', 1),
       (2, '2021-07-11', 2),
       (3, '2021-07-11', 3),
       (4, '2021-07-17', 1),
       (5, '2021-07-17', 2);

INSERT INTO lunchs (id, name, price, menu_id)
values (1, 'Sandwich', 2.55, 1),
       (2, 'Some food', 4.99, 1),
       (3, 'Some drink', 1.25, 2),
       (4, 'Hamburger', 3.44, 2),
       (5, 'Pizza1', 6.01, 2),
       (6, 'Pizza2', 6.01, 3),
       (7, 'Pizza3', 6.01, 3),
       (8, 'Pizza33', 6.01, 4),
       (9, 'Pizza22', 6.01, 4),
       (10, 'Pizza31', 6.01, 5);

INSERT INTO votes (id, restaurant_id, user_id)
values (1, 1, 1);

INSERT INTO votes (id, date, restaurant_id, user_id)
values (2,'2021-07-10', 2, 2),
       (3,'2021-07-11', 1, 1),
       (4,'2021-07-12', 3, 2),
       (5,'2021-07-15', 1, 1),
       (6,'2021-07-17', 1, 2),
       (7,'2021-07-17', 1, 1),
       (8,'2021-08-03', 1, 1);



INSERT INTO menus (id, create_date, restaurant_id )
VALUES (6, now(), 1);

INSERT INTO lunchs (id, name, price, menu_id)
values (11, 'Pie', 4.55, 6);