CREATE TABLE $table (
    test_date varchar(20),
    test_type varchar(20),
    op_count varchar(20),
    thread_num int,
    overall_tps varchar(20),
    Insert_AverageLatency varchar(20),
    Insert_99thPercentileLatency varchar(20),
    Read_AverageLatency varchar(20),
    Read_99thPercentileLatency varchar(20),
    Update_AverageLatency varchar(20),
    Update_99thPercentileLatency varchar(20),
    RMW_AverageLatency varchar(20),
    RMW_99thPercentileLatency varchar(20),
    primary key(test_date,test_type,op_count,thread_num),
    index td_tt_oc_tn_index (test_date,test_type,op_count,thread_num) engine=BTREE
) engine=BTREE