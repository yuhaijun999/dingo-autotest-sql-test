CREATE TABLE $table (
    id int,
    name varchar(64),
    age int,
    class_no int array not null default array[101,102,103],
    PRIMARY KEY (id)
) ENGINE=TXN_LSM
