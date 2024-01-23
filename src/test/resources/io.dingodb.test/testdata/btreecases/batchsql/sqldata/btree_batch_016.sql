CREATE TABLE b (
    id int not null auto_increment,
    name varchar(32),
    age int,
    PRIMARY KEY (id)
) engine=LSM;
insert into b values(1,'zhangsan',18),(2,'lisi',22),(3,'wangwu',33);
select * from b