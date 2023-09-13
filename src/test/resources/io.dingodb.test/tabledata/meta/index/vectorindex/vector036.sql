CREATE TABLE $table (
    id bigint not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) parameters(type=hnsw, metricType=METRIC_TYPE_COSINE, dimension=8, efConstruction=40, nlinks=32),
    primary key(id)
)