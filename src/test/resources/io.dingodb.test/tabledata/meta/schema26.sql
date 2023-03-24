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
    PRIMARY KEY (id)
) partition by range values (10001),(50001),(100001),(150001) with (propKey=propValue)