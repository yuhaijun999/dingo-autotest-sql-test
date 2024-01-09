CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(id)
) engine=BTREE partition by range values (10001),(100001),(1000001),(10000001),(100000001) with (propKey=propValue)