create table readers
(
	id int auto_increment,
    name varchar(50) not null,
    dob date not null,
    phone char(20) not null,
    primary key (id, name, dob, phone)
);

alter table readers auto_increment = 110001;
insert into readers(name, dob, phone) values('Đỗ Duy Kiên', '2002-12-19', '0963448172');
