CREATE TABLE $table (
    id int,
    name varchar(20),
    user_info any,
    age int,
    amount double,
    primary key(id)
) engine=TXN_BTREE