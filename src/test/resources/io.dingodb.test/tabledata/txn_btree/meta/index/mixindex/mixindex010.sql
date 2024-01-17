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
    feature float array not null,
    user_info any,
    feature_id bigint not null,
    PRIMARY KEY (id),
    index name_index (name) with (amount) engine=TXN_BTREE,
    index age_index (age) engine=TXN_BTREE,
    index birthday_index (birthday) with (gmt,update_time) engine=TXN_BTREE,
    index feature_index vector(feature_id, feature) engine=TXN_BTREE partition by hash partitions=5 parameters(type=hnsw, metricType=L2, dimension=64, efConstruction=40, nlinks=32)
) engine=TXN_BTREE