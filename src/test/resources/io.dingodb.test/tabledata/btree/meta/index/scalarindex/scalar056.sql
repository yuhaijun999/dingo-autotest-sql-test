CREATE TABLE $table(
    id INT,
    name VARCHAR(32),
    age INT,
    gmt BIGINT,
    price FLOAT,
    amount DOUBLE,
    address VARCHAR(255),
    birthday DATE,
    create_time TIME,
    update_time TIMESTAMP,
    zip_code varchar(20),
    is_delete BOOLEAN,
    INDEX age_index (age),
    PRIMARY KEY (id)
) engine=BTREE partition by hash partitions=5