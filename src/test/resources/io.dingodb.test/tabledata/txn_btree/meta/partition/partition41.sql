CREATE TABLE $table (
    name varchar(32) not null,
    age int,
    amount double,
    primary key(name,amount)
) engine=TXN_BTREE partition by range values (Nike,1000.0),(adidas,10000.0),(z,99999.99) with (propKey=propValue)
