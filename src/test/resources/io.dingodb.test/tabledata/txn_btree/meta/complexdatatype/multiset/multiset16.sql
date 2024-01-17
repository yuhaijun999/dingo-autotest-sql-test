CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    update_time timestamp,
    send_time timestamp multiset not null default multiset['1949-10-01 14:30:00','1999-12-12 08:00:00','2019-09-30 23:00:59'],
    primary key(id)
) engine=TXN_BTREE
