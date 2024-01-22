CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    price float multiset,
    primary key(id)
) ENGINE=TXN_LSM
