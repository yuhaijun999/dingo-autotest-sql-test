CREATE TABLE $table (
    id int,
    name varchar(32),
    age int,
    gmt bigint,
    price DOUBLE,
    amount DOUBLE,
    address varchar(255),
    birthday DATE,
    create_time TIME,
    update_time TIMESTAMP,
    zip_code varchar(10),
    is_delete boolean,
    PRIMARY KEY (id)
) engine=BTREE;
insert into $table values
(1,'zhangsan',18,99,0.0,23.50,'beijing','1998-04-06','08:10:10','2022-04-08 18:05:07',null,true),
(2,'lisi',-25,13989023458,1234.56,895,' beijing haidian ','1988-02-05','06:15:08','2000-02-29 00:00:00',null,false),
(3,'l3',55,0,-349.08,12389245204.123,'wuhan NO.1 Street','2022-03-04','07:03:15','1999-02-28 23:59:59',null,false);
drop table $table;
CREATE TABLE $table (
    id int,
    name varchar(32),
    age int,
    gmt bigint,
    price DOUBLE,
    amount DOUBLE,
    address varchar(255),
    birthday DATE,
    create_time TIME,
    update_time TIMESTAMP,
    zip_code varchar(10),
    is_delete boolean,
    PRIMARY KEY (id)
) engine=BTREE;
select * from $table