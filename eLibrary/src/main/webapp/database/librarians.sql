create table librarians
(
	id int auto_increment,
    name varchar(50) not null,
    dob date not null,
    phone char(12) not null,
    email char(50),
    password char(100) not null,
    primary key (id, dob, phone)
);
alter table librarians auto_increment = 100001;
