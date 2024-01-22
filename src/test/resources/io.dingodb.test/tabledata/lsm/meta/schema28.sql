CREATE TABLE $table (
    id varchar(64), 
    name varchar(64), 
    age int, 
    amount double, 
    address varchar(255), 
    primary key(id)
) ENGINE=LSM replica = 1