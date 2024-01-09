CREATE TABLE $table (
    id bigint not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    primary key(id),
    index feature_index vector(feature_id, feature) partition by hash partitions=5 parameters(type=flat, metricType=L2, dimension=64)
) engine=BTREE