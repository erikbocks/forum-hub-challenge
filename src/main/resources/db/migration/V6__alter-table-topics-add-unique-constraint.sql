alter table topics add constraint unique_topic unique(title, message);