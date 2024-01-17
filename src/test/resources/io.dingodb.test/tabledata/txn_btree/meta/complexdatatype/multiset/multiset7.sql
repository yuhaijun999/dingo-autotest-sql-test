CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    run_inter date multiset,
    primary key(id)
) engine=TXN_BTREE
