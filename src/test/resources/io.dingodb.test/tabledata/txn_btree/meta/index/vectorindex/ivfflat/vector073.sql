CREATE TABLE $table (
    id bigint auto_increment not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) engine=TXN_BTREE parameters(type=ivfflat, metricType=INNER_PRODUCT, dimension=1, ncentroids=64),
    primary key(id)
) engine=TXN_BTREE
