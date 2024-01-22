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
    index gmt_id_index (gmt,id) ENGINE=TXN_LSM partition by range values(100),(2147483648),(0)
) ENGINE=TXN_LSM
