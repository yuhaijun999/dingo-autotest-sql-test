CREATE TABLE $table (
    id int auto_increment not null,
    name varchar(32),
    age int,
    amount DOUBLE,
    PRIMARY KEY (id)
) engine=BTREE auto_increment=100