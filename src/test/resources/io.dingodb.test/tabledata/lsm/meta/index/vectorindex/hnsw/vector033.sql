CREATE TABLE $table (
    id bigint not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) ENGINE=LSM parameters(type=hnsw, metricType=INNER_PRODUCT, dimension=256, efConstruction=40, nlinks=32),
    primary key(id)
) ENGINE=LSM