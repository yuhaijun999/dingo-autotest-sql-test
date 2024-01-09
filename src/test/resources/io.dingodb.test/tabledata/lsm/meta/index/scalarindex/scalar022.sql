CREATE TABLE $table (
    id bigint,
    name varchar(32),
    age int,
    gmt bigint,
    price FLOAT,
    amount DOUBLE,
    address varchar(255),
    birthday DATE,
    create_time TIME,
    update_time TIMESTAMP,
    zip_code varchar(20),
    is_delete boolean,
    PRIMARY KEY (id),
    index name_gmt_update_time_index (name,gmt,update_time) partition by range values('Z',-100,'1999-02-28 00:59:59'),('tTATtt',100,'2020-02-29 05:53:44'),('z',2147483647,'2024-05-04 12:00:00')
)