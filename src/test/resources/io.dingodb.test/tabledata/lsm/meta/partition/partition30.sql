CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    create_time time,
    primary key(create_time, id)
) ENGINE=LSM partition by range values (08:30:00),(14:00:00),(19:00:00) with (propKey=propValue)