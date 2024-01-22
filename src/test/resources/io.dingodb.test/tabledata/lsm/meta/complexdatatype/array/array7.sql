CREATE TABLE $table (
    id int,
    name varchar(64),
    age int,
    amount DOUBLE,
    run_inter date array,
    PRIMARY KEY (id)
) ENGINE=LSM