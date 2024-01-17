CREATE TABLE $table (
    id varchar(64), 
    name varchar(64), 
    age int, 
    amount double, 
    address varchar(255), 
    primary key(id)
) engine=TXN_BTREE replica = 1
