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
    PRIMARY KEY (id,birthday),
    index name_index (name) with (price,gmt) partition by range values ('Z'),('z') replica=3
) engine=BTREE partition by range values (10,'2000-01-01'),(20,'2020-12-31') replica=2