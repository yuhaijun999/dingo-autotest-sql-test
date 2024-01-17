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
    PRIMARY KEY (name,id),
    index birthday_amount_index (birthday,amount) engine=TXN_BTREE partition by range values ('1995-10-01',-100,'Z',15),('2005-08-08',100,'z',30)
) engine=TXN_BTREE
