CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    user_info varchar multiset not null,
    primary key(id)
) engine=BTREE