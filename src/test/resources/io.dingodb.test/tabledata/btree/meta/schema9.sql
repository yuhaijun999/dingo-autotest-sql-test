CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(id)
) engine=BTREE partition by range values (0),(10),(20),(30) with (propKey=propValue)