CREATE TABLE $table (
    w3cschool_author varchar(255) DEFAULT NULL,
    w3cschool_count int DEFAULT NULL,
    PRIMARY KEY (w3cschool_author)
) engine=TXN_BTREE
