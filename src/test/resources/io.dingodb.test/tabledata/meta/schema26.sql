CREATE TABLE $table (
    id int,
    name varchar(32),
    age int,
    gmt bigint,
    price DOUBLE,
    amount DOUBLE,
    address varchar(255),
    birthday DATE,
    create_time TIME,
    update_time TIMESTAMP,
    zip_code varchar(10),
    is_delete boolean,
    PRIMARY KEY (id)
) partition by range values (1001),(5001),(10001),(15001) with (propKey=propValue)