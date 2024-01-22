CREATE TABLE $table (
    id int,
    name varchar(64),
    age int,
    amount DOUBLE,
    user_info varchar array,
    PRIMARY KEY (id)
) ENGINE=TXN_LSM
