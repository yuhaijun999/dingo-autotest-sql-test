CREATE TABLE $table (
    id int,
    name varchar(64),
    address varchar(255),
    user_info varchar array not null default array['male','BJ','18'],
    PRIMARY KEY (id)
) ENGINE=TXN_LSM
