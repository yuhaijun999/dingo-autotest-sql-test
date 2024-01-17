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
    index name_index (name) engine=TXN_BTREE,
    index age_index (age) engine=TXN_BTREE,
    index price_index (price) engine=TXN_BTREE partition by range values (0.0),
    index ct_index (create_time) engine=TXN_BTREE
) engine=TXN_BTREE
