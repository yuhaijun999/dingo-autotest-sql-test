CREATE TABLE $table (
    name varchar(32) not null,
    age int,
    amount double,
    id int,
    primary key(name,id)
) engine=TXN_BTREE partition by range values (Ww,0),(ad,100),(mq,10000) with (propKey=propValue)
