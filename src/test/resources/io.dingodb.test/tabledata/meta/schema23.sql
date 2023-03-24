CREATE TABLE $table (
    id int,
    name varchar(32),
    age int,
    gmt bigint,
    price float,
    amount DOUBLE,
    address varchar(255),
    birthday DATE,
    create_time TIME,
    update_time TIMESTAMP,
    zip_code char,
    is_delete boolean,
    PRIMARY KEY (update_time)
)