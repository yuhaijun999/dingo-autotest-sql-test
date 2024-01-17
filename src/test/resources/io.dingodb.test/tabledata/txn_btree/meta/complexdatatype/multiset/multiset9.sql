CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    send_time timestamp multiset,
    primary key(id)
) engine=TXN_BTREE
