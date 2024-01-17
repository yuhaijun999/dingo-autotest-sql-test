CREATE TABLE $table (
    name varchar(20),
    age int,
    birthday date,
    primary key(name)
) engine=TXN_BTREE ttl=300
