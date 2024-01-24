CREATE SCHEMA IF NOT EXISTS S;
CREATE TABLE S.M (
    id int not null auto_increment,
    name varchar(32),
    age int,
    PRIMARY KEY (id)
) engine=LSM;
insert into S.M values(1,'zhangsan',18),(2,'lisi',22),(3,'wangwu',33);
select id,name,age from S.M