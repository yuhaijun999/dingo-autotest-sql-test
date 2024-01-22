CREATE TABLE $table (
    id int not null auto_increment,
    name varchar(32),
    age int,
    PRIMARY KEY (id)
) engine=TXN_LSM;
insert into $table values(1,'zhangsan',18);
insert into $table(name,age) values('lisi',22);
insert into $table values (100, 'HaHa', 35);
insert into $table(name,age) values('BiBi',44);
insert into $table values (500, 'GoGo', 55);
insert into $table(name,age) values('LaLa',66);
insert into $table values (10000, 'Java', 77);
insert into $table(name,age) values('Python',88);
select * from $table