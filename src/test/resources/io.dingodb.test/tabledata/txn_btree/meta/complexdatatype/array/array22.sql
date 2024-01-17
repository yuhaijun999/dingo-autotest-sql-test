CREATE TABLE $table (
    id int,
    user_info varchar array,
    name varchar(20),
    PRIMARY KEY (id)
) engine=TXN_BTREE
