CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(id,age,name)
) engine=TXN_BTREE partition by range values (0,18),(100,50),(10000,60) with (propKey=propValue)
