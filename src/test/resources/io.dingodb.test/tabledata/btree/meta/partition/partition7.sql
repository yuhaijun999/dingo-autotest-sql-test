CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(name)
) engine=BTREE partition by range values (d) with (propKey=propValue)