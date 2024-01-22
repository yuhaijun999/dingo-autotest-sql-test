CREATE TABLE $table (
    id bigint not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    primary key(id),
    index feature_index vector(feature_id, feature) partition by range values (100) parameters(type=hnsw, metricType=L2, dimension=32, efConstruction=40, nlinks=32)
)