CREATE TABLE $table (
    uuid varchar(64),
    phone varchar(11),
    status int,
    acct_date date,
    reg_time timestamp,
    total_gmt double,
    primary key(uuid,phone,acct_date)
) engine=BTREE