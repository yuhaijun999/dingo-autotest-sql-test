CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    birthday date,
    primary key(id,birthday)
) ENGINE=LSM partition by range values (0,1989-12-31),(99,2000-01-01),(500,2010-07-01) with (propKey=propValue)