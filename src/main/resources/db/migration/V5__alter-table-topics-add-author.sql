alter table topics add author bigint unsigned not null;
alter table topics add constraint fk_author_topics foreign key (author) references users(user_id);