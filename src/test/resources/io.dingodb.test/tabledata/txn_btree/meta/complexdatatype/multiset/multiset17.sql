CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    is_delete boolean,
    in_use boolean multiset not null default multiset[true],
    primary key(id)
) engine=TXN_BTREE
