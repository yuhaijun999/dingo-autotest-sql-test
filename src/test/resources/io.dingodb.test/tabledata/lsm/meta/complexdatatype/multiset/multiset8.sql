CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    test_time time multiset,
    primary key(id)
) ENGINE=LSM