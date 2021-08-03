CREATE TABLE users
(
    id         INTEGER                 NOT NULL AUTO_INCREMENT,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX user_unique_email_idx on users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX restaurants_unique_name_idx on restaurants (name);

CREATE TABLE menus
(
    id            INTEGER            NOT NULL AUTO_INCREMENT,
    create_date   DATE DEFAULT now() NOT NULL,
    restaurant_id INTEGER            NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
create unique index menus_unique_date_restaurant_idx
    on menus (RESTAURANT_ID, create_date);

CREATE TABLE lunchs
(
    id      INTEGER      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    price   REAL         NOT NULL,
    menu_id INTEGER      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE
);

create unique index lunchs_unique_name_menu_idx
    on LUNCHS (menu_id, name);

CREATE TABLE votes
(
    id            INTEGER            NOT NULL AUTO_INCREMENT,
    date          DATE DEFAULT now() NOT NULL,
    restaurant_id INTEGER            NOT NULL,
    user_id       INTEGER            NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
create unique index votes_unique_date_user_idx
    on votes (date, user_id);