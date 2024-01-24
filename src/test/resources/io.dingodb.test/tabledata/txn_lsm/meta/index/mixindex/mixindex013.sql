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
    PRIMARY KEY (id),
    index birthday_index (birthday) ENGINE=TXN_LSM partition by range values ('1990-07-07'),('2010-07-01'),('2023-01-01'),
    index feature_index vector(feature_id, feature) ENGINE=TXN_LSM parameters(type=hnsw, metricType=L2, dimension=64, efConstruction=40, nlinks=32)
) ENGINE=TXN_LSM