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
    PRIMARY KEY (id, birthday),
    index name_index (name) with (amount) ENGINE=LSM partition by hash partitions=10,
    index age_index (age) ENGINE=LSM partition by range values (20),(55),(80),
    index birthday_index (birthday) with (gmt,update_time) ENGINE=LSM,
    index feature_index vector(feature_id, feature) ENGINE=LSM partition by hash partitions=5 parameters(type=hnsw, metricType=L2, dimension=64, efConstruction=40, nlinks=32)
) ENGINE=LSM partition by hash partitions=10