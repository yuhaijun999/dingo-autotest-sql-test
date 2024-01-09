CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(id)
) engine=BTREE partition by range values (100),(100),(256),(65536) with (propKey=propValue)