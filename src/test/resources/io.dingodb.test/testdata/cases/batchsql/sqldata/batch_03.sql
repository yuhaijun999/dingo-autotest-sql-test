CREATE TABLE $table(id int, name varchar(20), age int, amount double, address varchar(255), birthday date, create_time time, update_time timestamp, is_delete boolean, primary key (id));
INSERT INTO $table values(1,'zhangsan',18,23.50,'beijing','1998-04-06', '08:10:10', '2022-04-08 18:05:07', true);
SELECT * FROM $table;
DROP table $table;
create table $table(id int, name varchar(32) not null, age int, amount double, primary key(id));
INSERT INTO $table values(1, 'Alice', 18, 3.5);
SELECT * FROM $table