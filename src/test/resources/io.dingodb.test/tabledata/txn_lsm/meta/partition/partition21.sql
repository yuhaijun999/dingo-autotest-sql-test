CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(id,age)
) ENGINE=TXN_LSM partition by range values (11),(100) with (propKey=propValue)
