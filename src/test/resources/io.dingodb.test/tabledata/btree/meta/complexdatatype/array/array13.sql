CREATE TABLE $table (
    id int,
    name varchar(64),
    amount double,
    price double array not null default array[1234.56,0.01],
    PRIMARY KEY (id)
) engine=BTREE