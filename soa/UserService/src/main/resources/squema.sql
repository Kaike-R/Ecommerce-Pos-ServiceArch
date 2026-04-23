--create schema UserDatabase;

CREATE TABLE IF NOT EXISTS userdatabase.users (
    id SERIAL primary key,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(100),
    roles VARCHAR(10)
);


-- CREATE TABLE users (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     name VARCHAR(100) NOT NULL,
--     email VARCHAR(100) NOT NULL,
--     phone VARCHAR(20),
--
-- );