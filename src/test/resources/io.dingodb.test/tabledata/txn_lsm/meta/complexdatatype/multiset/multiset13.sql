CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    price double multiset not null default multiset[1234.56,0.01],
    primary key(id)
) ENGINE=TXN_LSM
