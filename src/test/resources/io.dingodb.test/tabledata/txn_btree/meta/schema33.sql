CREATE TABLE IF NOT EXISTS $table (
        OrderId INT NOT NULL,
        ProductName VARCHAR(50) NOT NULL,
        OrderTime INT NOT NULL DEFAULT Unix_TimeStamp(now()),
        PRIMARY KEY(OrderId)
) engine=TXN_BTREE
