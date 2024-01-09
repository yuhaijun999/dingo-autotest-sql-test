CREATE TABLE $table (
    name varchar(32) not null,
    age int,
    create_time time,
    primary key(create_time)
) engine=BTREE partition by range values (18:00:00) with (propKey=propValue)