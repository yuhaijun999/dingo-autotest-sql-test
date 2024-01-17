CREATE TABLE $table (
    name varchar(32) not null,
    age int,
    amount double,
    is_delete boolean,
    primary key(name,is_delete)
) engine=TXN_BTREE partition by range values (a,true) with (propKey=propValue)
