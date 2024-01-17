CREATE TABLE $table (
    id int,
    name varchar(64),
    age int,
    amount DOUBLE,
    send_time timestamp array,
    PRIMARY KEY (id)
) engine=TXN_BTREE
