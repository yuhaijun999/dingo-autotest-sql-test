CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(id,age)
) engine=TXN_BTREE partition by range values (10,18),(10,18) with (propKey=propValue)
