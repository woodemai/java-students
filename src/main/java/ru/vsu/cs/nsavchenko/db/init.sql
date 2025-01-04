-- init.sql

-- Создание таблицы групп
CREATE TABLE IF NOT EXISTS groups (
    id SERIAL PRIMARY KEY,
    group_name VARCHAR(255) NOT NULL
);

-- Создание таблицы студентов
CREATE TABLE IF NOT EXISTS students (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    group_id INTEGER REFERENCES groups(id)
);

-- Вставка начальных данных в таблицу групп
INSERT INTO groups (group_name) VALUES ('Группа 1');
INSERT INTO groups (group_name) VALUES ('Группа 2');

-- Вставка начальных данных в таблицу студентов
INSERT INTO students (first_name, last_name, group_id) VALUES ('Иван', 'Иванов', 1);
INSERT INTO students (first_name, last_name, group_id) VALUES ('Петр', 'Петров', 1);
INSERT INTO students (first_name, last_name, group_id) VALUES ('Светлана', 'Сидорова', 2);

