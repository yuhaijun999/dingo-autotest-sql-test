CREATE TABLE $table (
    name varchar(32) not null,
    age int,
    amount double,
    birthday date,
    primary key(birthday)
) engine=BTREE partition by range values (2000-01-01) with (propKey=propValue)