CREATE TABLE $table (
    id varchar,
    name varchar(20),
    age int,
    amount double,
    primary key(id,name)
) engine=TXN_BTREE ttl=300