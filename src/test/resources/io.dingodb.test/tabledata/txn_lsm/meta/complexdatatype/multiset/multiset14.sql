CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    birthday date,
    run_inter date multiset not null default multiset['2022-06-18','2022-11-11','2023-01-01'],
    primary key(id)
) ENGINE=TXN_LSM
