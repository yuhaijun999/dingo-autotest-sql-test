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
    index id_address_birthday_index (id,address,birthday) engine=TXN_BTREE partition by range values(10,'J','2000-01-01'),(20,'bei','2015-12-31')
) engine=TXN_BTREE
