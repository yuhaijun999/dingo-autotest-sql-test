CREATE TABLE $table (
    name varchar(20), 
    age int, 
    class_no int multiset not null, 
    amount double, 
    id int, 
    primary key(id)
) engine=BTREE