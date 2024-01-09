create table $table (name varchar(20), id varchar(20) not null, primary key(id)) engine=BTREE;
insert into $table (name,id) values ('2','2');
insert into $table (name,id) values ('3','134006132');
drop table $table;
create table $table (name varchar(20), id int not null, primary key(id)) engine=BTREE;
insert into $table (name,id) values ('2',2);
insert into $table (name,id) values ('3',134006132)