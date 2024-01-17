CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(id,age)
) engine=BTREE partition by range values (0,18),(100,18) with (propKey=propValue)