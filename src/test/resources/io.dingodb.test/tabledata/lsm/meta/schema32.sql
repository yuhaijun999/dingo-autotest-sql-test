CREATE TABLE IF NOT EXISTS $table (
        `dimension` VARCHAR(255) NOT NULL,
        `metric_id` INT NOT NULL,
        `gmt` DOUBLE NOT NULL,
        `phone` VARCHAR NOT NULL,
        `byte_array` BINARY NOT NULL,
        PRIMARY KEY(dimension, metric_id)
) ENGINE=LSM partition by range values ('139e39',5),('258f36',10),('396783',15)