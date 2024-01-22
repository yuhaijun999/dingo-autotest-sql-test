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
    PRIMARY KEY (id,name,update_time),
    index name_index (name) ENGINE=TXN_LSM replica=1,
    index age_index (age) ENGINE=TXN_LSM replica=3
) ENGINE=TXN_LSM
