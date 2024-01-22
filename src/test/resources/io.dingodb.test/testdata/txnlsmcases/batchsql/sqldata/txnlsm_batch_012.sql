CREATE TABLE $table (
    id int not null auto_increment,
    name varchar(32),
    age int,
    PRIMARY KEY (id)
) engine=TXN_LSM;
insert into $table values(-1,'zhangsan',18);
insert into $table(name,age) values('lisi',22);
select * from $table