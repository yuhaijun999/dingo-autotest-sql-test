CREATE TABLE $table (
    id bigint auto_increment not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) parameters(type=ivfpq, metricType=COSINE, dimension=2, ncentroids=32, nsubvector=2, nbits_per_idx=4),
    primary key(id)
)