CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    birthday date,
    primary key(name)
) partition by range values (10086) with (propKey=propValue)