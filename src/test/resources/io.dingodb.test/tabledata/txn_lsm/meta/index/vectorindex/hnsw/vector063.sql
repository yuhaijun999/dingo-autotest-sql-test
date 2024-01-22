CREATE TABLE $table (
    id int auto_increment not null,
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
    index feature_index vector(feature_id, feature) ENGINE=TXN_LSM parameters(type=hnsw, metricType=L2, dimension=8, efConstruction=40, nlinks=32)
) ENGINE=TXN_LSM
