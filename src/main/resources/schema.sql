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

CREATE TABLE restaurant
(
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX restaurant_unique_name_idx on restaurant (name);

CREATE TABLE lunch
(
    id            INTEGER            NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255)       NOT NULL,
    price         REAL               NOT NULL,
    date          DATE DEFAULT now() NOT NULL,
    restaurant_id INTEGER            NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
create unique index LUNCH_UNIQUE_RESTAURANT_ID_NAME_DATE_IDX
    on LUNCH (RESTAURANT_ID, NAME, DATE);