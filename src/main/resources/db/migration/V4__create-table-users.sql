create table users(
    user_id serial primary key,
    name varchar(100) not null,
    email varchar(100) unique not null,
    password varchar(100) not null,
    role varchar(20) not null
);