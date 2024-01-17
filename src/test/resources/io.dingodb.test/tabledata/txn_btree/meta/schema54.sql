CREATE TABLE $table (
    id int NOT NULL,
    name varchar(50) NOT NULL,
    sex varchar(10) DEFAULT 'female',
    borndate timestamp DEFAULT '1987-01-01 00:00:00',
    phone varchar(11) NOT NULL,
    boyfriend_id int DEFAULT NULL,
    PRIMARY KEY (id)
) engine=TXN_BTREE ttl=3600 partition by hash partitions=5