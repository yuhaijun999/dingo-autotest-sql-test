CREATE TABLE $table (
    id bigint auto_increment not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) parameters(type=ivfflat, metricType=COSINE, dimension=32, ncentroids=64),
    primary key(id)
) engine=BTREE