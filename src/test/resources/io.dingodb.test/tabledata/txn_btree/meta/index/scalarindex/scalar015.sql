CREATE TABLE $table (
    id int,
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
    index name_index (name) engine=TXN_BTREE,
    index amount_index (amount) engine=TXN_BTREE,
    index name_address_age_create_time (name,address,age,create_time) engine=TXN_BTREE
) engine=TXN_BTREE
