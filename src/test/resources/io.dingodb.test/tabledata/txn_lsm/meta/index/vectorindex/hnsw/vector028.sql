CREATE TABLE $table (
    id bigint not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) ENGINE=TXN_LSM parameters(type=hnsw, metricType=L2, dimension=1024, efConstruction=40, max_elements=1000, nlinks=32),
    primary key(id)
) ENGINE=TXN_LSM
