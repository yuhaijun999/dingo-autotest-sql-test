CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    update_time timestamp,
    primary key(id,update_time)
) ENGINE=TXN_LSM partition by range values (100,1999-09-01 00:00:00),(999,2009-07-31 18:30:00) with (propKey=propValue)
