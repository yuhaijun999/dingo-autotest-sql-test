CREATE TABLE $table (
    id bigint auto_increment not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) parameters(type=ivfpq, metricType=INNER_PRODUCT, dimension=256, ncentroids=3, nsubvector=8, nbitsPerIdx=2),
    primary key(id)
)