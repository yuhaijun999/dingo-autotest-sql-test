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
    index name_index (name) engine=BTREE,
    index age_index (age) engine=BTREE,
    index price_index (price) engine=BTREE partition by range values (0.0),
    index ct_index (create_time) engine=BTREE
) engine=BTREE