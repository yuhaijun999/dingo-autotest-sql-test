CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    birthday date,
    primary key(id)
) ENGINE=LSM partition by range values (10) with (propKey=propValue)