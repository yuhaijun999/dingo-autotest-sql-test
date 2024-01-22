CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    primary key(name)
) ENGINE=TXN_LSM partition by range values (adidas),(Nike),(puma) with (propKey=propValue)
