CREATE TABLE $table (
    id bigint,
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
    index price_gmt_zc_index (price,gmt,zip_code) with (id,name,age,gmt,price,amount,address,birthday,create_time,update_time,zip_code,is_delete) ENGINE=LSM
) ENGINE=LSM