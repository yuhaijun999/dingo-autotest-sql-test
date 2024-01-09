CREATE TABLE $table(id int NOT NULL, name varchar(20), age int, amount double, birthday date, PRIMARY KEY(id)) engine=BTREE;
INSERT INTO $table values(1,'aa',18,1111.1,'2022-11-11'),(2,'bb',20,2.2,'2023-01-01');
SELECT * FROM $table;
UPDATE $table SET age=100 WHERE id=1;
INSERT INTO $table values(3,'cc',35,3.3,'1987-04-06'),(4,'dd',40,4.4,'1960-11-12'),(8,NULL,NULL,NULL,'1999-12-31');
SELECT * FROM $table;
UPDATE $table SET name = 'dingo' WHERE id>1 AND id<4;
SELECT * FROM $table;
INSERT INTO $table values(5,'ff',55,5.5,'2010-12-01'),(6,'test',38,99.99,'2015-08-20'),(7,NULL,NULL,NULL,'2000-12-31');
SELECT * FROM $table;
DELETE FROM $table WHERE name='dingo';
SELECT * FROM $table;
UPDATE $table SET amount=99.99;
SELECT * FROM $table;
DELETE FROM $table WHERE birthday < '2000-01-01';
UPDATE $table SET birthday='2023-01-06' WHERE age<>100;
SELECT * FROM $table