CREATE TABLE $table (
    ID INT AUTO_INCREMENT NOT NULL,
    NAME VARCHAR(32) NOT NULL,
    AGE INT DEFAULT 20,
    GMT BIGINT,
    PRICE FLOAT,
    AMOUNT DOUBLE NOT NULL DEFAULT 0.0,
    ADDRESS VARCHAR(255) DEFAULT 'BEIJING',
    BIRTHDAY DATE,
    CREATE_TIME TIME,
    UPDATE_TIME TIMESTAMP,
    ZIP_CODE VARCHAR(20) DEFAULT NULL,
    IS_DELETE BOOLEAN,
    USER_INFO ANY,
    BYTE_ARRAY BINARY NOT NULL, 
    FEATURE1 FLOAT ARRAY NOT NULL, 
    FEATURE2 FLOAT ARRAY NOT NULL,
    FEATURE1_ID BIGINT NOT NULL,
    FEATURE2_ID BIGINT NOT NULL,
    PRIMARY KEY(ID,BIRTHDAY),
    INDEX NAME_INDEX(NAME), 
    INDEX AGE_INDEX(AGE) WITH (CREATE_TIME) PARTITION BY HASH PARTITIONS=5,
    INDEX GMT_ADDRESS_INDEX(GMT,ADDRESS) WITH (PRICE,UPDATE_TIME) PARTITION BY RANGE VALUES (10000,'BEIJING'),(1000000,'SHANGHAI') REPLICA=3, 
    INDEX FEATURE1_INDEX VECTOR(FEATURE1_ID, FEATURE1) WITH (PRICE) PARTITION BY HASH PARTITIONS=5 REPLICA=1 PARAMETERS(type=flat, metricType=L2, dimension=8), 
    INDEX FEATURE2_INDEX VECTOR(FEATURE2_ID, FEATURE2) PARTITION BY RANGE VALUES (10),(20) PARAMETERS(type=hnsw, metricType=COSINE, dimension=8, efConstruction=40, nlinks=32)
) ENGINE=LSM ttl=3600 partition by range values (10,'1990-01-01'),(20,'2000-01-01'),(30,'2010-01-01') REPLICA=3 DEFAULT CHARSET=utf8 COMMENT='information_test_info'