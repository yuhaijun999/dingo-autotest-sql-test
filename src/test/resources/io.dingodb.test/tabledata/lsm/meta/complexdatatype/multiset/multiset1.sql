CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    class_no int multiset,
    primary key(id)
) ENGINE=LSM