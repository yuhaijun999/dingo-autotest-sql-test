CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(id,age)
) ENGINE=TXN_LSM partition by range values (100,0),(100,50),(100,100) with (propKey=propValue)
