CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(amount)
) ENGINE=LSM partition by range values (0.0), (9999.99), (100000.0) with (propKey=propValue)