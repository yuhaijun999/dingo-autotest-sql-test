CREATE TABLE $table (
    id bigint auto_increment not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) parameters(type=ivfpq, metricType=COSINE, dimension=24, ncentroids=6, nsubvector=3, nbitsPerIdx=3),
    primary key(id)
)