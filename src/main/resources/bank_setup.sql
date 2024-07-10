drop table if exists "user";

create table "user"(
	user_id integer PRIMARY KEY autoincrement,
	username text UNIQUE,
	password text
);

insert into "user" (username, password) values ('admin', 1234);


