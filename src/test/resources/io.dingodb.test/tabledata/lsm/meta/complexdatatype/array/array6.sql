CREATE TABLE $table (
    id int,
    name varchar(64),
    age int,
    amount DOUBLE,
    price float array,
    PRIMARY KEY (id)
) ENGINE=LSM