create table topics(
    topic_id serial primary key,
    title varchar(150) not null,
    message varchar(255) not null,
    creationDate date not null,
    status varchar(50) not null
);