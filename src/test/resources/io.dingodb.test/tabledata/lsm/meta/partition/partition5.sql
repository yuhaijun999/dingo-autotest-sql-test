CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(id)
) ENGINE=LSM partition by range values (10000),(1000),(50000),(10),(655360),(12345678),(-1000000),(0) with (propKey=propValue)