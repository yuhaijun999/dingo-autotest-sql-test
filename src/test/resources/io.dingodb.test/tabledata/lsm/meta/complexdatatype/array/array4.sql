CREATE TABLE $table (
    id int,
    name varchar(64),
    age int,
    amount DOUBLE,
    user_info char array,
    PRIMARY KEY (id)
) ENGINE=LSM