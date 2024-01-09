CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    create_time time,
    test_time time multiset not null default multiset['00:00:00','12:30:00','23:59:59'],
    primary key(id)
) engine=BTREE