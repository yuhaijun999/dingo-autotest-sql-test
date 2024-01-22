CREATE TABLE $table (
    id bigint not null, 
    name varchar(32),
    age int,
    amount double,
    feature1 float array not null,
    feature_id1 bigint not null,
    feature2 float array not null,
    feature_id2 bigint not null,
    primary key(id),
    index name_index (name),
    index feature1_index vector(feature_id1, feature1) parameters(type=flat, metricType=L2, dimension=8),
    index feature2_index vector(feature_id2, feature2) parameters(type=hnsw, metricType=COSINE, dimension=64, efConstruction=40, nlinks=32)
)