CREATE TABLE $table (
    id int auto_increment not null,
    name varchar(32),
    age int,
    amount DOUBLE,
    PRIMARY KEY (id)
) ENGINE=TXN_LSM auto_increment=100
