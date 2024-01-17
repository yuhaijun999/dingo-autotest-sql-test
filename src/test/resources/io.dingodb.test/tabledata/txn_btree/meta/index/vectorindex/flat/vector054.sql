CREATE TABLE $table (
    id bigint not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    primary key(id),
    index feature_index vector(feature_id, feature) engine=TXN_BTREE partition by hash partitions=100 parameters(type=flat, metricType=COSINE, dimension=8)
) engine=TXN_BTREE
