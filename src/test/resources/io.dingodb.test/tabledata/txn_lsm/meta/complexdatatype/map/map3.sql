CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    user_info any not null default map['sex','male','birthday','2022-01-01'],
    primary key(id)
) ENGINE=TXN_LSM
