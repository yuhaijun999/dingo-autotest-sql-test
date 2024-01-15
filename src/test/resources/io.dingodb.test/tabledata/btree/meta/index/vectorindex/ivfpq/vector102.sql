CREATE TABLE $table (
    id bigint auto_increment not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) engine=BTREE parameters(type=ivfpq, metricType=INNER_PRODUCT, dimension=32, ncentroids=8, nsubvector=4, nbitsPerIdx=3),
    primary key(id)
) engine=BTREE