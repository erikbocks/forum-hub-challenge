create table answers(
    answer_id serial primary key,
    message varchar(255) not null,
    topic bigint unsigned not null,
    creation_date date not null,
    author bigint unsigned not null
);

alter table answers add constraint unique_answer unique(message);
alter table answers add constraint fk_topic_answers foreign key (topic) references topics(topic_id);
alter table answers add constraint fk_author_answers foreign key (author) references users(user_id);