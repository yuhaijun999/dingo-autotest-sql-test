CREATE TABLE $table (
    id int, 
    name varchar(20), 
    age int, 
    gmt bigint, 
    price float, 
    amount double, 
    address varchar(255), 
    birthday date, 
    create_time time, 
    update_time timestamp, 
    zip_code varchar(6), 
    is_delete boolean, 
    primary key(id), 
    index d_iatd(amount) ENGINE=LSM
) ENGINE=LSM