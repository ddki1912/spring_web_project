create table admins
(
	id char(20) not null,
    password char(20) not null,
    primary key (id)
);

insert into admins(id, password) values('admin','admin123');

insert into admins(id, password) values('adminuser', 'admin456');