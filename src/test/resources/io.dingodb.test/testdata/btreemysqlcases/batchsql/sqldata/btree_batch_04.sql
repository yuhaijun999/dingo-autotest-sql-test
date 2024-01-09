CREATE TABLE $table(id int, name varchar(20), age int, amount double, account varchar(32), primary key (id)) engine=BTREE;
INSERT INTO $table values(1,'zhangsan',18,23.50,'6225900138567890'),(2,'lisi',55,99.99,'6225880178560985');
SELECT * from $table where account='6225880178560985';
SELECT account FROM $table