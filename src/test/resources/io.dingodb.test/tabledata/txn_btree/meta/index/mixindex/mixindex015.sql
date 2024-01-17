CREATE TABLE $table (
    id bigint not null, 
    name varchar(32),
    age int,
    amount double,
    feature1 float array not null,
    feature_id1 bigint not null,
    feature2 float array not null,
    feature_id2 bigint not null,
    index name_index (name) engine=TXN_BTREE,
    primary key(id),
    index feature1_index vector(feature_id1, feature1) engine=TXN_BTREE parameters(type=flat, metricType=L2, dimension=8),
    index feature2_index vector(feature_id2, feature2) engine=TXN_BTREE parameters(type=hnsw, metricType=COSINE, dimension=64, efConstruction=40, nlinks=32)
) engine=TXN_BTREE
