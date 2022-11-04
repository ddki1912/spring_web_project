create table borrowbook
(
	readerId bigint not null,
    bookId bigint not null,
    borrowOn date not null,
    returnStatus char(50) not null,
    primary key (readerId, bookId, borrowOn, returnStatus),
    foreign key (readerId) references readers(id),
    foreign key (bookId) references books(id)
);