CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(id)
) engine=TXN_BTREE partition by range values (12345),(12345),(12345) with (propKey=propValue)
