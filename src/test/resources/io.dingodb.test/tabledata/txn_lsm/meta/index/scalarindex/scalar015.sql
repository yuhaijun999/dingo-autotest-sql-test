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
    index name_index (name) ENGINE=TXN_LSM,
    index amount_index (amount) ENGINE=TXN_LSM,
    index name_address_age_create_time (name,address,age,create_time) ENGINE=TXN_LSM
) ENGINE=TXN_LSM
