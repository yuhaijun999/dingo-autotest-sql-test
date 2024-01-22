CREATE TABLE $table (
    name varchar(32) not null,
    age int,
    amount double,
    is_delete boolean,
    primary key(name,is_delete)
) ENGINE=TXN_LSM partition by range values (a,true) with (propKey=propValue)
