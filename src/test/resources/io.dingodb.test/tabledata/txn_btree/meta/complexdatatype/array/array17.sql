CREATE TABLE $table (
    id int,
    name varchar(64),
    is_delete boolean,
    in_use boolean array not null default array[true],
    PRIMARY KEY (id)
) engine=TXN_BTREE
