CREATE TABLE $table (
    id int NOT NULL,
    boyName varchar(20) DEFAULT NULL,
    userCP int DEFAULT NULL,
    PRIMARY KEY (id)
) partition by hash partitions=5