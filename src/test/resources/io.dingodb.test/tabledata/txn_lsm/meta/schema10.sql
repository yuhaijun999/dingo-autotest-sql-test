CREATE TABLE $table (
    id int,
    name varchar not null,
    age int,
    amount double,
    primary key(id,name)
) ENGINE=TXN_LSM partition by range values (0,M),(10,a),(20,t),(30,z) with (propKey=propValue)
