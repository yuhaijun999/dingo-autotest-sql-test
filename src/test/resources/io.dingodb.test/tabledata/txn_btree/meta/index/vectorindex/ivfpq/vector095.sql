CREATE TABLE $table (
    id bigint auto_increment not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) engine=TXN_BTREE parameters(type=ivfpq, metricType=L2, dimension=64, ncentroids=32, nsubvector=8, nbitsPerIdx=3),
    primary key(id)
) engine=TXN_BTREE
