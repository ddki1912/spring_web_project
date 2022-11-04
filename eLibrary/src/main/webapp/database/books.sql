create table books
(
	id bigint auto_increment,
    name varchar(200) not null,
    author varchar(200) not null,
    quantity bigint not null,
    borrowed bigint not null,
    primary key (id, name, quantity) 
);
