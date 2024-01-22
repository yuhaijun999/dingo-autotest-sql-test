CREATE TABLE $table (
    id int,
    name varchar(64),
    age int,
    amount DOUBLE,
    in_use boolean array,
    PRIMARY KEY (id)
) ENGINE=TXN_LSM
