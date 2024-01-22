CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(id,name)
) ENGINE=LSM partition by range values (0,Ww),(100,ad),(10000,mq) with (propKey=propValue)