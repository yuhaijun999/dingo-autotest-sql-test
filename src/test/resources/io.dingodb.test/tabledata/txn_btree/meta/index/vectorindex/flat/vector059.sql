CREATE TABLE $table (
    id bigint not null, 
    name varchar(32),
    age int,
    amount double,
    feature float array not null,
    feature_id bigint not null,
    primary key(id),
    index feature_index vector(feature_id, feature) with (age,amount) engine=TXN_BTREE partition by hash partitions=32 replica=1 parameters(type=flat, metricType=L2, dimension=8)
) engine=TXN_BTREE ttl=86400 partition by range values (199),(599) replica=2
