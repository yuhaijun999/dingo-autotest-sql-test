CREATE TABLE $table (
    user_info varchar array,
    amount double,
    id int,
    name varchar(20),
    age int,
    primary key(id)
) ENGINE=TXN_LSM
