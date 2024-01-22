CREATE TABLE $table (
    id int,
    name varchar(32),
    age int,
    address varchar(255),
    primary key(name)
) ENGINE=LSM partition by range values ('') with (propKey=propValue)