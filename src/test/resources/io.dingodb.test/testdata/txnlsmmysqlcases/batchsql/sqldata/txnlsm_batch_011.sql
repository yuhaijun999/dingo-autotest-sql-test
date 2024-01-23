CREATE TABLE $table (
    id bigint not null auto_increment,
    name varchar(32),
    age int,
    PRIMARY KEY (id)
) engine=TXN_LSM;
insert into $table(name,age) values('lisi',22);
insert into $table values (100, 'HaHa', 35);
insert into $table values(2,'zhangsan',18);
insert into $table(name,age) values('BiBi',44);
insert into $table(name,age) values('C++',99);
insert into $table values (1000000, 'GoGo', 55);
insert into $table(name,age) values('LaLa',66);
insert into $table values (10000, 'Java', 77);
insert into $table(name,age) values('Python',88);
insert into $table values (2147483647, 'Java', 77);
insert into $table(name,age) values('JS',88);
insert into $table(name,age) values('CSS',101);
insert into $table values (-10, 'HTML', 77);
insert into $table(name,age) values('C',201);
select * from $table