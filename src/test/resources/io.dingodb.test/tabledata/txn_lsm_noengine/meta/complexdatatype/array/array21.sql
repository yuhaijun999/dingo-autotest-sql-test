CREATE TABLE $table (
    id int,
    name varchar(64),
    age int,
    amount DOUBLE,
    user_info varchar array not null,
    PRIMARY KEY (id)
)