CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    user_info any not null,
    primary key(id)
) ENGINE=LSM