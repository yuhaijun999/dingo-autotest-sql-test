CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    create_time time,
    primary key(id,create_time)
) engine=TXN_BTREE partition by range values (100,08:00:00),(999,15:30:00),(10000,19:00:00) with (propKey=propValue)
