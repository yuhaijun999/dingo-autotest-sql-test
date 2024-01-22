CREATE TABLE $table (
    id bigint auto_increment not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    index feature_index vector(feature_id, feature) ENGINE=LSM parameters(type=flat, metricType=L2, dimension=8),
    primary key(id)
) ENGINE=LSM