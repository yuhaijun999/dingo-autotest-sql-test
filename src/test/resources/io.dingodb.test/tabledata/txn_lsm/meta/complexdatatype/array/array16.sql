CREATE TABLE $table (
    id int,
    name varchar(64),
    update_time timestamp,
    send_time timestamp array not null default array['1949-10-01 14:30:00','1999-12-12 08:00:00','2019-09-30 23:00:59'],
    PRIMARY KEY (id)
) ENGINE=TXN_LSM
