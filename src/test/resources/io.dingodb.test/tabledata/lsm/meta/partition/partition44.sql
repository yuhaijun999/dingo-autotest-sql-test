CREATE TABLE $table (
    name varchar(32) not null,
    age int,
    amount double,
    update_time timestamp,
    primary key(name,update_time)
) ENGINE=LSM partition by range values (a,1998-12-31 10:00:00),(M,2011-11-11 18:30:00) with (propKey=propValue)