CREATE TABLE $table (
    id int not null auto_increment,
    name varchar(32),
    age int,
    PRIMARY KEY (id)
);
insert into $table values(1,'zhangsan',18);
insert into $table(name,age) values('lisi',22);
delete from $table where id=2;
insert into $table(name,age) values('wangwu',33);
insert into $table values(100,'zhangsan2',28);
insert into $table(name,age) values ('hello',44),('world',55);
delete from $table where name='hello' or id=101;
insert into $table(name,age) values ('Java',66);
delete from $table where age=66;
insert into $table(name,age) values ('Python',77),('C++',88),('Go',99);
select * from $table