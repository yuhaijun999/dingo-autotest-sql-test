CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    user_info any,
    data any,
    good_info any,
    primary key(id)
) ENGINE=TXN_LSM
