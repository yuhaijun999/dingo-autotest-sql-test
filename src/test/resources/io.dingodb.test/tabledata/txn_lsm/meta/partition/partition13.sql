CREATE TABLE $table (
    name varchar(32) not null,
    age int,
    amount double,
    birthday date,
    primary key(birthday)
) ENGINE=TXN_LSM partition by range values (2000-01-01),(1980-12-31),(1949-10-01),(2022-07-11) with (propKey=propValue)
