CREATE TABLE $table (
    id bigint auto_increment not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) parameters(type=ivfpq, metricType=INNER_PRODUCT, dimension=128, ncentroids=3, nsubvector=8, nbits_per_idx=2),
    primary key(id)
)