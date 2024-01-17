CREATE TABLE $table (
    id int not null,
    name varchar(32) not null,
    age int,
    amount double,
    birthday date,
    create_time time,
    primary key(create_time)
) engine=TXN_BTREE partition by range values (10:30:30), (18:00:00), (21:59:59) with (propKey=propValue)
