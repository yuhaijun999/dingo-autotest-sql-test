CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(amount, id)
) partition by range values (0.0),(99999.99) with (propKey=propValue)