CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(name,id)
) ENGINE=TXN_LSM partition by range values (Ww,0),(ad,100),(mq,10000) with (propKey=propValue)
