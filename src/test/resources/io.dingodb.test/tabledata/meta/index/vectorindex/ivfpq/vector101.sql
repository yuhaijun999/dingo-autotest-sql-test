CREATE TABLE $table (
    id bigint auto_increment not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) parameters(type=ivfpq, metricType=INNER_PRODUCT, dimension=8, ncentroids=8, nsubvector=2, nbits_per_idx=3),
    primary key(id)
)