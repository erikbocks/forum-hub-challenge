create table courses(
    course_id serial primary key,
    name varchar(100) unique not null,
    category varchar(10) not null
);