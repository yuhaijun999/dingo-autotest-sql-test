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
    feature float array not null,
    user_info any,
    feature_id bigint not null,
    PRIMARY KEY (id,name,birthday),
    index address_index (address) ENGINE=TXN_LSM,
    index age_index (price) ENGINE=TXN_LSM,
    index feature_index vector(feature_id, feature) ENGINE=TXN_LSM partition by hash partitions=5 parameters(type=hnsw, metricType=L2, dimension=64, efConstruction=40, nlinks=32)
) ENGINE=TXN_LSM
