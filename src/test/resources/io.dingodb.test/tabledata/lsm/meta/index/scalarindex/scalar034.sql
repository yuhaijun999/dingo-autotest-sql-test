CREATE TABLE $table (
    id int,
    name varchar(32),
    age int,
    gmt bigint,
    price FLOAT,
    amount DOUBLE,
    address varchar(255),
    birthday DATE,
    create_time TIME,
    update_time TIMESTAMP,
    zip_code varchar(20),
    is_delete boolean,
    PRIMARY KEY (id),
    index zc_index (zip_code) with (name,age,gmt,price,amount,address,birthday,create_time,update_time,is_delete) ENGINE=LSM
) ENGINE=LSM