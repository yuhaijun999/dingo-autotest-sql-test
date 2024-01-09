CREATE TABLE $table (
    name varchar(32) not null,
    age int,
    amount double,
    birthday date,
    create_time time,
    update_time timestamp,
    primary key(update_time)
) engine=BTREE partition by range values (2020-01-01 18:00:00) with (propKey=propValue)