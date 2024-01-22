CREATE TABLE $table (
    name varchar(32) not null,
    age int,
    update_time timestamp,
    primary key(update_time)
) ENGINE=TXN_LSM partition by range values (1991-06-18 14:15:01), (2010-10-10 10:10:10), (2020-01-01 18:00:00) with (propKey=propValue)
