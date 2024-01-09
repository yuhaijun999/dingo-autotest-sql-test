CREATE TABLE $table (
    id varchar,
    name varchar(20),
    age int,
    amount double,
    address varchar(255),
    birthday date,
    create_time time,
    update_time timestamp,
    is_delete boolean,
    primary key (id,name,update_time)
) engine=BTREE