CREATE TABLE $table (
    id bigint auto_increment not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) parameters(type=ivfpq, metricType=L2, dimension=1, ncentroids=64, nsubvector=1, nbitsPerIdx=8),
    primary key(id)
) engine=BTREE