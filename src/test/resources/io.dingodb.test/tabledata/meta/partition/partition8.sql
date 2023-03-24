CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(name)
) partition by range values (a),(m),(u) with (propKey=propValue)