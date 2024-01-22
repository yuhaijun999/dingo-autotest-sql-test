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
    index name_index (name) with (id) ENGINE=TXN_LSM,
    index birthday_index (birthday) with (age) ENGINE=TXN_LSM,
    index amount (amount) with (amount) ENGINE=TXN_LSM
) ENGINE=TXN_LSM
