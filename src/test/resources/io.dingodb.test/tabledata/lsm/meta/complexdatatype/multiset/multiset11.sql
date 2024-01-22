CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    class_no int multiset not null default multiset[0,1024,-65536],
    primary key(id)
) ENGINE=LSM