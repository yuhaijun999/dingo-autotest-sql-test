CREATE TABLE l (
    id int not null auto_increment,
    name varchar(32),
    age int,
    PRIMARY KEY (id)
) engine=TXN_LSM;
insert into l values(1,'zhangsan',18),(2,'lisi',22),(3,'wangwu',33);
select * from l