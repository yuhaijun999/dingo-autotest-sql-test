CREATE TABLE $table (
    id BIGINT AUTO_INCREMENT NOT NULL ,
    age BIGINT NOT NULL,
    gender VARCHAR(12) NOT NULL,
    hobby VARCHAR(12)  NOT NULL,
    color FLOAT ARRAY NOT NULL,
    INDEX hobby_index2 (hobby) ENGINE=TXN_LSM PARTITION BY RANGE values('eat'), ('sleep') REPLICA = 3,
    INDEX color_index vector(age, color) WITH (gender) ENGINE=TXN_LSM PARTITION BY RANGE values(10) parameters(type=flat, metricType=L2, dimension=512),
    PRIMARY KEY(id)
) ENGINE=TXN_LSM
