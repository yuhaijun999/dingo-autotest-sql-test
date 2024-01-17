CREATE TABLE v (
    id int not null auto_increment,
    name varchar(32),
    age int,
    PRIMARY KEY (id)
);
insert into v values(1,'zhangsan',18),(2,'lisi',22),(3,'wangwu',33);
select * from v