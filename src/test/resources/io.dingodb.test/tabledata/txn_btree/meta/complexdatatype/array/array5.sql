CREATE TABLE $table (
    id int,
    name varchar(64),
    age int,
    amount DOUBLE,
    price double array,
    PRIMARY KEY (id)
) engine=TXN_BTREE
