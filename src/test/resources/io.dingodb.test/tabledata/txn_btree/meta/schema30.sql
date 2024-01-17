CREATE TABLE $table (
    uuid int auto_increment,
    PRIMARY KEY (uuid)
) engine=TXN_BTREE replica=3 auto_increment=100
