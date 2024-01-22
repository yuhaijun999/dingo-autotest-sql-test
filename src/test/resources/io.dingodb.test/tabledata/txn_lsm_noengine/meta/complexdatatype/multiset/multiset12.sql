CREATE TABLE $table (
    id int,
    name varchar(20),
    age int,
    amount double,
    user_info varchar multiset not null default multiset['male','BJ','18'],
    primary key(id)
)