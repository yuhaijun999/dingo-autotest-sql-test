CREATE TABLE IF NOT EXISTS $table (
  `id` int(20) NOT NULL,
  `info` blob,
  PRIMARY KEY (`id`)
) ENGINE=TXN_BTREE
