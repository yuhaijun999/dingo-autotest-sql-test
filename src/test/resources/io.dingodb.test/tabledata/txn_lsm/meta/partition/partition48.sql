CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    write_date date,
    primary key(id,write_date)
) ENGINE=TXN_LSM ttl=7200 partition by range values (100000, '1995-01-01'),(10000000,'2010-07-01'),(100000000,'2022-09-30') with (propKey=propValue)
