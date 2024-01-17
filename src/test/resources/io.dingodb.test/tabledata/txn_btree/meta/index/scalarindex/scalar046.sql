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
    PRIMARY KEY (id,name,update_time),
    index name_index (name) engine=TXN_BTREE replica=1,
    index age_index (age) engine=TXN_BTREE replica=3
) engine=TXN_BTREE
