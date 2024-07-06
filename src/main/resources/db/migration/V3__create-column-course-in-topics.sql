alter table topics add column course bigint unsigned not null;
alter table topics add constraint fk_course_topics foreign key (course) references courses(course_id);