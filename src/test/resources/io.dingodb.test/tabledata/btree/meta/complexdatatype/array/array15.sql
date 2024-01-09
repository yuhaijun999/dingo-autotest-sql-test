CREATE TABLE $table (
    id int,
    name varchar(64),
    create_time time,
    test_time time array not null default array['00:00:00','12:30:00','23:59:59'],
    PRIMARY KEY (id)
) engine=BTREE