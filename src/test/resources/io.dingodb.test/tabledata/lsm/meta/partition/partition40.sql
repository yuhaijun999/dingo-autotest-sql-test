CREATE TABLE $table (
    name varchar(32) not null,
    age int,
    amount double,
    address varchar(255),
    primary key(name,address)
) ENGINE=LSM partition by range values (Li,Bj),(wang,sh) with (propKey=propValue)