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
    index name_index (name) with (price,birthday) ENGINE=LSM,
    index age_index (age) with (amount) ENGINE=LSM,
    index address_ut_index (address, update_time) with (zip_code) ENGINE=LSM
) ENGINE=LSM