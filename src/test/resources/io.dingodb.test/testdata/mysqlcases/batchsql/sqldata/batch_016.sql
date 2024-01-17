CREATE TABLE M (
    id int not null auto_increment,
    name varchar(32),
    age int,
    PRIMARY KEY (id)
);
insert into M values(1,'zhangsan',18),(2,'lisi',22),(3,'wangwu',33);
select id,name,age from M