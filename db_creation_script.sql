CREATE DATABASE IF NOT EXISTS store;
USE store;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS items;

CREATE TABLE items
(
    id    int            NOT NULL AUTO_INCREMENT,
    name  varchar(255)            DEFAULT NULL,
    price decimal(10, 2) NOT NULL DEFAULT 0.00,
    PRIMARY KEY (id)
);

--
-- Data for table `employee`
--

INSERT INTO items (name, price)
VALUES ('Milk', 2.25),
       ('Bread', 1.25),
       ('Butter', 5.05),
       ('Tomato', 2.50),
       ('Onion', 2.00);