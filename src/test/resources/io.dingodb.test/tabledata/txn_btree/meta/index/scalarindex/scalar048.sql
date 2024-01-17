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
    index name_index (name) engine=TXN_BTREE,
    PRIMARY KEY (id),
    index age_index (age) engine=TXN_BTREE partition by range values (10),
    index price_index (price) engine=TXN_BTREE partition by range values (0.0),(100.0)
) engine=TXN_BTREE
