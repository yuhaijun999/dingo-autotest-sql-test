CREATE TABLE $table (
    id int,
    name varchar(64),
    age int,
    amount DOUBLE,
    class_no int array,
    PRIMARY KEY (id)
) ENGINE=TXN_LSM
