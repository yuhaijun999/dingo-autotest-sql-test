CREATE TABLE $table (
    id int,
    name varchar(64),
    age int,
    amount DOUBLE,
    class_no bigint array,
    PRIMARY KEY (id)
) engine=TXN_BTREE