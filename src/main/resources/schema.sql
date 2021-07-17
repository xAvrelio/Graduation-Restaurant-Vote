CREATE TABLE restaurant
(
    id        INTEGER      NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
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
