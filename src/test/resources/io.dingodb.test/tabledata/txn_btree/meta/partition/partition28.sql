CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(name,id,age)
) engine=TXN_BTREE partition by range values (a) with (propKey=propValue)
