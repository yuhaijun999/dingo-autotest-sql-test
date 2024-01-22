CREATE TABLE $table (
    uuid int auto_increment,
    PRIMARY KEY (uuid)
) ENGINE=TXN_LSM replica=3 auto_increment=100
