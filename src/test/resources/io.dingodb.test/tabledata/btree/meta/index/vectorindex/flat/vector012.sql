CREATE TABLE $table (
    id bigint not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) engine=BTREE parameters(type=flat, metricType=INNER_PRODUCT, dimension=256),
    primary key(id)
) engine=BTREE