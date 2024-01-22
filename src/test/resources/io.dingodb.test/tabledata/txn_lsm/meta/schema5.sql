CREATE TABLE $table (
    id varchar,
    name varchar(20),
    age int,
    amount double,
    primary key(id,name)
)  ENGINE=TXN_LSM ttl=300
