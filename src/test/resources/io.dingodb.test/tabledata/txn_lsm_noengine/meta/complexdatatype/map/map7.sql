CREATE TABLE $table (
    id int,
    name varchar(64),
    age int,
    amount double,
    address varchar(255),
    birthday date,
    create_time time,
    update_time timestamp,
    is_delete boolean,
    data any,
    primary key(id)
)