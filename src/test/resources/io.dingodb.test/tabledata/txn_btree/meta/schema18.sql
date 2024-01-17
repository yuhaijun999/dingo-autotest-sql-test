CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int null default 18,
    gmt bigint null default 13800000000,
    price FLOAT null default 0.0,
    amount DOUBLE null default 100.000000,
    address varchar(255) null default 'BJ',
    birthday DATE null default '2023-03-15',
    create_time TIME null default '18:00:00',
    update_time TIMESTAMP null default '2023-03-15 09:30:00',
    zip_code varchar(10) null default null,
    is_delete boolean null default false,
    PRIMARY KEY (id)
) engine=TXN_BTREE
