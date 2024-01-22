CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(id,amount)
) partition by range values (0,-1000000.0),(10000,0.0),(1000000,1000000.0) with (propKey=propValue)