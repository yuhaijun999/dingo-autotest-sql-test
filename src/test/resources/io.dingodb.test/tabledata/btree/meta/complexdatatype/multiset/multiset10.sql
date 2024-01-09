CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    in_use boolean multiset,
    primary key(id)
) engine=BTREE