CREATE TABLE $table (
    id int NOT NULL,
    boyName varchar(20) DEFAULT NULL,
    userCP int DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=TXN_LSM partition by hash partitions=5
