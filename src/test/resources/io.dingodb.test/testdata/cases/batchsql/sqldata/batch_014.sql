CREATE TABLE $table (
    id int not null auto_increment,
    name varchar(32),
    age int,
    PRIMARY KEY (id)
);
insert into $table(name,age) values('zhangsan',18);
insert into $table(name,age) values('lisi',22);
truncate table $table;
insert into $table(name,age) values('wangwu',33)