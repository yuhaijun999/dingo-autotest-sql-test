CREATE TABLE $table (
    id bigint auto_increment not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) engine=TXN_BTREE parameters(type=ivfpq, metricType=COSINE, dimension=8, ncentroids=12, nsubvector=2, nbitsPerIdx=4),
    primary key(id)
) engine=TXN_BTREE
