CREATE TABLE $table (
    name varchar(32) not null,
    age int,
    amount double,
    primary key(amount)
) engine=TXN_BTREE partition by range values (2000.0) with (propKey=propValue)
