CREATE TABLE $table (
    id bigint not null auto_increment,
    name varchar(32),
    age int,
    PRIMARY KEY (id)
) engine=TXN_LSM;
insert into $table values(-2,'zhangsan',18);
insert into $table(name,age) values('lisi',22);
select * from $table