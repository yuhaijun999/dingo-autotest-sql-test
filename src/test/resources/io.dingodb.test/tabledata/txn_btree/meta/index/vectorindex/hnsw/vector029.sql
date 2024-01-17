CREATE TABLE $table (
    id bigint not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) engine=TXN_BTREE parameters(type=hnsw, metricType=INNER_PRODUCT, dimension=8, efConstruction=40, nlinks=32),
    primary key(id)
) engine=TXN_BTREE
