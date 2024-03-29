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
    index amount_index (amount) with (gmt) ENGINE=TXN_LSM partition by range values (-100.0),(0),(9999.9999) replica=3
) ENGINE=TXN_LSM
