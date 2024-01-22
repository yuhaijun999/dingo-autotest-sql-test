CREATE TABLE $table (
    id int,
    name varchar(64),
    birthday date,
    run_inter date array not null default array['2022-06-18','2022-11-11','2023-01-01'],
    PRIMARY KEY (id)
) ENGINE=LSM