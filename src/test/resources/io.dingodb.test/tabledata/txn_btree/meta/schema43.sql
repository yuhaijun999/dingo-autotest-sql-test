CREATE TABLE $table (
    uuid varchar(64) not null,
    name varchar(32) default 'dingo',
    age int not null default 18,
    gmt bigint not null default 13800000000,
    price FLOAT not null default 0.0,
    amount DOUBLE not null default 100.000000,
    address varchar(255) not null default 'BJ',
    birthday DATE not null default '2023-03-15',
    create_time TIME not null default '18:00:00',
    update_time TIMESTAMP not null default '2023-03-15 09:30:00',
    zip_code varchar(10) default '100100',
    is_delete boolean not null default false,
    PRIMARY KEY (name)
) engine=TXN_BTREE
