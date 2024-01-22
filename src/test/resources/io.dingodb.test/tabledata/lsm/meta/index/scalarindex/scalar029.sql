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
    PRIMARY KEY (id,name,update_time),
    index id_name_ut_index (id,name,update_time) with (age,gmt,price,amount,address,birthday,create_time,zip_code,is_delete) ENGINE=LSM
) ENGINE=LSM