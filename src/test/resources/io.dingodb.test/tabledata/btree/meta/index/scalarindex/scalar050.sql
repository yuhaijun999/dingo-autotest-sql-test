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
    index name_age_index (name,age) with (price,gmt) engine=BTREE partition by range values ('Dingo',0),('z',100) replica=3
) engine=BTREE partition by range values (10),(20) replica=3