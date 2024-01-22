CREATE TABLE $table (     
   `gender` bigint,
   `age` bigint,
   `grade` bigint,
   `is_loc` bigint,
   `is_m_new` bigint,
   `billing_type` bigint,
   `brand_id` float,
   `is_group_main` bigint,
   `is_fam_nt` bigint,
   `together_flag` bigint,
   `is_gotone` bigint,
   `is_sfz_cert` bigint,
   `area_id` bigint,
   `user_type` bigint,
   `is_rpc` bigint,
   `is_school_presenter` bigint,
   `is_contract` bigint,
   `is_fam_lan` bigint,
   `gprs_use_4g` bigint,
   `gprs_use_5g` bigint,
   `is_group_pay` bigint,
   `is_vice` bigint,
   `is_bill` bigint,
   `is_comm` bigint,
   `is_school` bigint,
   `is_group_keyperson` bigint,
   `is_bac` bigint,
   `is_qf_stop` bigint,
   `is_zd_stop` bigint,
   `is_y_new` bigint,
   `is_m_change` bigint,
   `is_fam_v` bigint,
   `if_mocard` bigint,
   `if_easy_churn_prome` bigint,
   `join_chl_type` varchar,
   `main_offer_eff_date` bigint,
   `main_offer_price` float,
   `con_onecard` float,
   `is_permonth_fee` varchar,
   `permonth_fee` float,
   `gprs_fee` float,
   `call_fee` float,
   `sms_fee` float,
   `join_date` bigint,
   `online_time` float,
   `last_term_fisrt_date` varchar,
   `last_term_last_date` varchar,
   `last_term_tx_days` float,
   `last_term_l_tx_days` float,
   `last_term_ll_tx_days` float,
   `last_term_sw_days` float,
   `last_term_l_sw_days` float,
   `last_term_ll_sw_days` float,
   `last_term_th_days` float,
   `last_term_l_th_days` float,
   `last_term_ll_th_days` float,
   `last_term_yy_dur` float,
   `last_term_l_yy_dur` float,
   `last_term_ll_yy_dur` float,
   `last_term_th_num` float,
   `last_term_l_th_num` float,
   `last_term_ll_th_num` float,
   `last_term_zj_dur` float,
   `last_term_l_zj_dur` float,
   `last_term_ll_zj_dur` float,
   `last_term_gprs` float,
   `last_term_l_gprs` float,
   `last_term_ll_gprs` float,
   `last_term_5g_gprs` float,
   `last_term_l_5g_gprs` float,
   `last_term_ll_5g_gprs` float,
   `last_term_tx_jz_num` float,
   `most_term_fisrt_date` varchar,
   `most_term_last_date` varchar,
   `most_term_tx_days` float,
   `most_term_l_tx_days` float,
   `most_term_ll_tx_days` float,
   `most_term_sw_days` float,
   `most_term_l_sw_days` float,
   `most_term_ll_sw_days` float,
   `most_term_th_days` float,
   `most_term_l_th_days` float,
   `most_term_ll_th_days` float,
   `most_term_yy_dur` float,
   `most_term_l_yy_dur` float,
   `most_term_ll_yy_dur` float,
   `most_term_th_num` float,
   `most_term_l_th_num` float,
   `most_term_ll_th_num` float,
   `most_term_zj_dur` float,
   `most_term_l_zj_dur` float,
   `most_term_ll_zj_dur` float,
   `most_term_gprs` float,
   `most_term_l_gprs` float,
   `most_term_ll_gprs` float,
   `most_term_5g_gprs` float,
   `most_term_l_5g_gprs` float,
   `most_term_ll_5g_gprs` float,
   `most_term_tx_jz_num` float,
   `max_total_fee` float,
   `min_total_fee` float,
   `sum_total_fee` float,
   `mean_total_fee` float,
   `trend_total_fee` float,
   `bodong_total_fee` float,
   `total_fee` float,
   `total_fee_1` float,
   `total_fee_2` float,
   `total_fee_3` float,
   `total_fee_4` float,
   `total_fee_5` float,
   `total_fee_6` float,
   `max_total_tax_fee` float,
   `min_total_tax_fee` float,
   `sum_total_tax_fee` float,
   `mean_total_tax_fee` float,
   `trend_total_tax_fee` float,
   `bodong_total_tax_fee` float,
   `total_tax_fee` float,
   `total_tax_fee_1` float,
   `total_tax_fee_2` float,
   `total_tax_fee_3` float,
   `total_tax_fee_4` float,
   `total_tax_fee_5` float,
   `total_tax_fee_6` float,
   `mon_fee` float,
   `plan_fee` float,
   `call_mon_fee` float,
   `onnet_mon_fee` float,
   `oth_mon_fee` float,
   `out_plan_call_fee` float,
   `out_plan_local_call_fee` float,
   `out_plan_inner_rm_call_fee` float,
   `out_plan_intl_rm_call_fee` float,
   `out_plan_hmt_rm_call_fee` float,
   `oth_out_plan_call_fee` float,
   `out_plan_onnet_fee` float,
   `out_plan_gprs_fee` float,
   `out_plan_wlan_fee` float,
   `out_plan_sms_mms_fee` float,
   `out_plan_sms_fee` float,
   `ismp_fee` float,
   `acy_fee` float,
   `grp_prod_fee` float,
   `grp_pay_fee` float,
   `oth_fee` float,
   `plan_spl_fee` float,
   `zh_fee` float,
   `xz_fee` float,
   `fact_flow` float,
   `fact_call` float,
   `fact_sms` float,
   `flow` float,
   `flow_bhd` float,
   `call` float,
   `call_bhd` float,
   `sms` float,
   `sms_bhd` float,
   `arpu` float,
   `out_off_up_flow` float,
   `out_off_down_flow` float,
   `in_off_up_flow` float,
   `in_off_down_flow` float,
   `comm_days` float,
   `call_days` float,
   `onnet_days` float,
   `onnet_num` float,
   `call_num` float,
   `bill_dur` float,
   `moc_call_num` float,
   `moc_bill_dur` float,
   `gprs_miss_flow` float,
   `mob_gprs_miss_flow` float,
   `p2p_sms_up_cnt` float,
   `p2p_mms_up_cnt` float,
   `gprs_fee_fentan` float,
   `call_lac_ci_num` float,
   `gprs_miss_lac_ci_num` float,
   `gc_comm_lac_ci_num` float,
   `sph_num` float,
   `moc_short_num` float,
   `mtc_short_num` float,
   `call_trans_num` float,
   `calling_failed_cnt` float,
   `called_failed_cnt` float,
   `inner_roam_call_num` float,
   `inner_roam_bill_dur` float,
   `intl_roam_call_num` float,
   `intl_roam_bill_dur` float,
   `sum_up_flow` float,
   `sum_down_flow` float,
   `last_comm_date` bigint,
   `last_onnet_date` bigint,
   `last_call_date` bigint,
   `last_sms_date` bigint,
   `churn_target` varchar,
   `c_custid` varchar,
   primary key(c_custid)
) ENGINE=TXN_LSM
