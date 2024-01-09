CREATE TABLE $table (
    name varchar(32) not null,
    age int,
    amount double,
    primary key(amount)
) partition by range values (2000.0) with (propKey=propValue)