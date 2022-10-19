create table books
(
	id int auto_increment,
    name varchar(50) not null,
    author varchar(50),
    quantity int not null,
    borrowed char(20) not null,
    primary key (id, name, quantity) 
);
