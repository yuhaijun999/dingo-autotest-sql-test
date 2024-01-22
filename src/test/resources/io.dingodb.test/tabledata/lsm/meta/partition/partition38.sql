CREATE TABLE $table (
    id int,
    name varchar(32) not null,
    age int,
    amount double,
    is_delete boolean,
    primary key(id,is_delete)
) ENGINE=LSM partition by range values (10000,true),(99999,true) with (propKey=propValue)