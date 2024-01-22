CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    birthday date,
    primary key(name)
) ENGINE=TXN_LSM partition by range values (10086) with (propKey=propValue)
