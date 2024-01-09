CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    price double multiset,
    primary key(id)
) engine=BTREE