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
    index name_index (name) ENGINE=TXN_LSM,
    index age_index (age) ENGINE=TXN_LSM partition by range values (10),
    index price_index (price) ENGINE=TXN_LSM partition by range values (0.0),(100.0)
) ENGINE=TXN_LSM
