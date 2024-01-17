CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    birthday date,
    primary key(birthday,id)
) engine=TXN_BTREE partition by range values (1999-07-07),(2010-01-01),(2020-12-31) with (propKey=propValue)
