create table borrowbook
(
	readerId int not null,
    bookId int not null,
    borrowOn date not null,
    returnStatus char(50) not null,
    primary key (readerId, bookId, borrowOn, returnStatus),
    foreign key (readerId) references readers(id),
    foreign key (bookId) references books(id)
);