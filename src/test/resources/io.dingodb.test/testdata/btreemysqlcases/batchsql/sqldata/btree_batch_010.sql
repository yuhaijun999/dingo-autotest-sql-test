CREATE DATABASE myjarvex_btree;
USE myjarvex_btree;
DROP TABLE IF EXISTS myjarvex_btree.`CHAT_QU_DATA_BTREE_BTREE`;
CREATE TABLE myjarvex_btree.`CHAT_QU_DATA_BTREE` (
	user_id VARCHAR(255),
	chat_se_id VARCHAR(255),
	chat_history VARCHAR(255),
	create_time VARCHAR(255),
	flag VARCHAR(255),
	is_mark VARCHAR(255),
	chat_qu_id INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (chat_qu_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`CHAT_SE_DATA_BTREE`;
CREATE TABLE myjarvex_btree.`CHAT_SE_DATA_BTREE` (
	chat_name VARCHAR(255),
	chat_des VARCHAR(255),
	user_id VARCHAR(255),
	create_time VARCHAR(255),
	is_mark VARCHAR(255),
	chat_se_id INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (chat_se_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`COMMENT_BASE_INFO_BTREE`;
CREATE TABLE myjarvex_btree.`COMMENT_BASE_INFO_BTREE` (
	comment_des VARCHAR(255),
	user_id VARCHAR(255),
	is_mark INTEGER,
	status_id INTEGER,
	create_time VARCHAR(255),
	comment_id INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (comment_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`CREATIVE_BASE_INFO_BTREE`;
CREATE TABLE myjarvex_btree.`CREATIVE_BASE_INFO_BTREE` (
	creative_name VARCHAR(255),
	creative_type VARCHAR(255),
	creative_des VARCHAR(255),
	creative_answer VARCHAR(255),
	user_id VARCHAR(255),
	prompt VARCHAR(255),
	share_level VARCHAR(255),
	answer_dataset_id VARCHAR(255),
	is_mark VARCHAR(255),
	create_time VARCHAR(255),
	creative_icon VARCHAR(255),
	creative_id INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (creative_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`CREATIVE_QU_DATA_BTREE`;
CREATE TABLE myjarvex_btree.`CREATIVE_QU_DATA_BTREE` (
	user_id VARCHAR(255),
	creative_se_id VARCHAR(255),
	creative_history VARCHAR(255),
	create_time VARCHAR(255),
	is_mark VARCHAR(255),
	creative_qu_id INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (creative_qu_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`CREATIVE_SE_DATA_BTREE`;
CREATE TABLE myjarvex_btree.`CREATIVE_SE_DATA_BTREE` (
	creative_name VARCHAR(255),
	creative_des VARCHAR(255),
	create_time VARCHAR(255),
	creative_icon VARCHAR(255),
	is_mark VARCHAR(255),
	creative_se_id INTEGER NOT NULL,
	user_id VARCHAR(255) NOT NULL,
	PRIMARY KEY (creative_se_id, user_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`DATA_FILE_BTREE`;
CREATE TABLE myjarvex_btree.`DATA_FILE_BTREE` (
	data_name VARCHAR(255),
	data_des VARCHAR(255),
	user_id VARCHAR(255),
	file_categroy VARCHAR(255),
	dataset_id VARCHAR(255),
	data_char INTEGER,
	data_chunk INTEGER,
	data_path VARCHAR(255),
	data_split_path VARCHAR(255),
	create_time VARCHAR(255),
	status_id INTEGER,
	share_level VARCHAR(255),
	is_mark INTEGER,
	data_id INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (data_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`DATA_FILE_EXPERTISE_BTREE`;
CREATE TABLE myjarvex_btree.`DATA_FILE_EXPERTISE_BTREE` (
	question VARCHAR(255),
	answer VARCHAR(255),
	user_id VARCHAR(255),
	dataset_id VARCHAR(255),
	create_time VARCHAR(255),
	update_time VARCHAR(255),
	status_id INTEGER,
	is_mark VARCHAR(255),
	expertise_id VARCHAR(255) NOT NULL,
	PRIMARY KEY (expertise_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`DATA_FILEFT_BTREE`;
CREATE TABLE myjarvex_btree.`DATA_FILEFT_BTREE` (
	data_name VARCHAR(255),
	data_des VARCHAR(255),
	user_id VARCHAR(255),
	dataset_id VARCHAR(255),
	data_char VARCHAR(255),
	data_qa VARCHAR(255),
	data_chunk VARCHAR(255),
	data_path VARCHAR(255),
	data_qa_path VARCHAR(255),
	data_split_path VARCHAR(255),
	create_time VARCHAR(255),
	status_id INTEGER,
	is_mark INTEGER,
	data_enhance_path VARCHAR(255),
	is_enhance VARCHAR(255),
	data_id INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (data_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`DATA_FILESET_BTREE`;
CREATE TABLE myjarvex_btree.`DATA_FILESET_BTREE` (
	dataset_name VARCHAR(255),
	dataset_des VARCHAR(255),
	user_id VARCHAR(255),
	info_path VARCHAR(255),
	create_time VARCHAR(255),
	share_level INTEGER,
	icon VARCHAR(255),
	is_mark VARCHAR(255),
	dataset_id VARCHAR(255) NOT NULL,
	PRIMARY KEY (dataset_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`DATA_FT_LIST_BTREE`;
CREATE TABLE myjarvex_btree.`DATA_FT_LIST_BTREE` (
	data_id VARCHAR(255),
	chunk VARCHAR(255),
	question VARCHAR(255),
	answer VARCHAR(255),
	create_time VARCHAR(255),
	is_socre INTEGER,
	is_enhance VARCHAR(255),
	ft_id VARCHAR(255) NOT NULL,
	PRIMARY KEY (ft_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`DATASET_DINGO_BTREE`;
CREATE TABLE myjarvex_btree.`DATASET_DINGO_BTREE` (
	data_id VARCHAR(255),
	dingo_id VARCHAR(255),
	create_time VARCHAR(255),
	`Answer_dingo_id` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`Answer_dingo_id`)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`DATASET_FT_LIST_BTREE`;
CREATE TABLE myjarvex_btree.`DATASET_FT_LIST_BTREE` (
	data_id VARCHAR(255),
	chunk VARCHAR(255),
	question VARCHAR(255),
	answer VARCHAR(255),
	create_time VARCHAR(255),
	is_socre INTEGER,
	is_enhance VARCHAR(255),
	ft_id VARCHAR(255) NOT NULL,
	PRIMARY KEY (ft_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`DEPARTMENT_BTREE`;
CREATE TABLE myjarvex_btree.`DEPARTMENT_BTREE` (
	department_name VARCHAR(255) NOT NULL,
	department_database VARCHAR(255) NOT NULL,
	department_table VARCHAR(255) NOT NULL,
	department_pro_data VARCHAR(255) NOT NULL,
	is_mark INTEGER NOT NULL,
	leavl_id VARCHAR(255) NOT NULL,
	create_time VARCHAR(255) NOT NULL,
	update_time VARCHAR(255) NOT NULL,
	department_id INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (department_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`MODEL_BASE_INFO_BTREE`;
CREATE TABLE myjarvex_btree.`MODEL_BASE_INFO_BTREE` (
	model_server_name VARCHAR(255),
	model_des VARCHAR(255),
	model_type VARCHAR(255),
	model_params VARCHAR(255),
	share_level VARCHAR(255),
	model_tuning_path VARCHAR(255),
	model_params_path VARCHAR(255),
	modle_dataset_id VARCHAR(255),
	modle_base_id VARCHAR(255),
	user_id VARCHAR(255),
	finetuning_type VARCHAR(255),
	create_time VARCHAR(255),
	is_mark VARCHAR(255),
	model_id INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (model_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`OPERATION_BTREE`;
CREATE TABLE myjarvex_btree.`OPERATION_BTREE` (
	create_time BIGINT NOT NULL,
	update_time BIGINT NOT NULL,
	name VARCHAR(255) NOT NULL,
	code VARCHAR(255) NOT NULL,
	type VARCHAR(255) NOT NULL,
	remark VARCHAR(255),
	id INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (id)
) engine=BTREE AUTO_INCREMENT = 1000 COMMENT='功能操作表';
INSERT INTO myjarvex_btree.OPERATION_BTREE (CREATE_TIME,UPDATE_TIME,NAME,CODE,`TYPE`,REMARK,ID) VALUES
	 (1702282318591,1702282318591,'查看菜单','view','menu',NULL,1),
	 (1702282318591,1702282318591,'查看按钮','view','element',NULL,2),
	 (1702282318591,1702282318591,'所有权','own','dataset',NULL,3),
	 (1702282318591,1702282318591,'所有权','own','creativity',NULL,4),
	 (1702282318591,1702282318591,'所有权','own','app',NULL,5);
DROP TABLE IF EXISTS myjarvex_btree.`PERMISSION_BTREE`;
CREATE TABLE myjarvex_btree.`PERMISSION_BTREE` (
	create_time BIGINT NOT NULL,
	update_time BIGINT NOT NULL,
	type VARCHAR(255) NOT NULL,
	operation_id INTEGER NOT NULL,
	resource_id INTEGER NOT NULL,
	remark VARCHAR(255),
	status INTEGER NOT NULL,
	id INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (id)
) engine=BTREE AUTO_INCREMENT = 1000 COMMENT='权限信息表';
INSERT INTO myjarvex_btree.PERMISSION_BTREE (CREATE_TIME,UPDATE_TIME,`TYPE`,OPERATION_ID,RESOURCE_ID,REMARK,STATUS,ID) VALUES
	 (1702282318591,1702282318591,'menu',1,1,NULL,1,1),
	 (1702282318591,1702282318591,'menu',1,2,NULL,1,2),
	 (1702282318591,1702282318591,'menu',1,3,NULL,1,3),
	 (1702282318591,1702282318591,'menu',1,4,NULL,1,4),
	 (1702282318591,1702282318591,'menu',1,5,NULL,1,5),
	 (1702282318591,1702282318591,'menu',1,6,NULL,1,6),
	 (1702282318591,1702282318591,'element',2,7,NULL,1,7),
	 (1702282318591,1702282318591,'element',2,8,NULL,1,8),
	 (1702282318591,1702282318591,'element',2,9,NULL,1,9),
	 (1702282318591,1702282318591,'element',2,10,NULL,1,10);
INSERT INTO myjarvex_btree.PERMISSION_BTREE (CREATE_TIME,UPDATE_TIME,`TYPE`,OPERATION_ID,RESOURCE_ID,REMARK,STATUS,ID) VALUES
	 (1702282318591,1702282318591,'element',2,11,NULL,1,11),
	 (1702282318591,1702282318591,'element',2,12,NULL,1,12),
	 (1702282318591,1702282318591,'element',2,13,NULL,1,13),
	 (1702282318591,1702282318591,'element',2,14,NULL,1,14),
	 (1702282318591,1702282318591,'element',2,15,NULL,1,15),
	 (1702282318591,1702282318591,'element',2,16,NULL,1,16),
	 (1702282318591,1702282318591,'element',2,17,NULL,1,17),
	 (1702282318591,1702282318591,'element',2,18,NULL,1,18),
	 (1702282318591,1702282318591,'element',2,19,NULL,1,19),
	 (1702282318591,1702282318591,'element',2,20,NULL,1,20);
INSERT INTO myjarvex_btree.PERMISSION_BTREE (CREATE_TIME,UPDATE_TIME,`TYPE`,OPERATION_ID,RESOURCE_ID,REMARK,STATUS,ID) VALUES
	 (1702282318591,1702282318591,'element',2,21,NULL,1,21),
	 (1702282318591,1702282318591,'element',2,22,NULL,1,22),
	 (1702282318591,1702282318591,'element',2,23,NULL,1,23),
	 (1702282318591,1702282318591,'element',2,24,NULL,1,24),
	 (1702282318591,1702282318591,'element',2,25,NULL,1,25),
	 (1702282318591,1702282318591,'element',2,26,NULL,1,26),
	 (1702282318591,1702282318591,'element',2,27,NULL,1,27),
	 (1702282318591,1702282318591,'element',2,28,NULL,1,28),
	 (1702282318591,1702282318591,'element',2,29,NULL,1,29),
	 (1702282318591,1702282318591,'element',2,30,NULL,1,30);
INSERT INTO myjarvex_btree.PERMISSION_BTREE (CREATE_TIME,UPDATE_TIME,`TYPE`,OPERATION_ID,RESOURCE_ID,REMARK,STATUS,ID) VALUES
	 (1702282318591,1702282318591,'element',2,31,NULL,1,31),
	 (1702282318591,1702282318591,'element',2,32,NULL,1,32),
	 (1702282318591,1702282318591,'element',2,33,NULL,1,33),
	 (1702282318591,1702282318591,'element',2,34,NULL,1,34),
	 (1702282318591,1702282318591,'element',2,35,NULL,1,35),
	 (1702282318591,1702282318591,'element',2,36,NULL,1,36),
	 (1702282318591,1702282318591,'element',2,37,NULL,1,37),
	 (1702282318591,1702282318591,'element',2,38,NULL,1,38),
	 (1702282318591,1702282318591,'element',2,39,NULL,1,39),
	 (1702282318591,1702282318591,'element',2,40,NULL,1,40);
INSERT INTO myjarvex_btree.PERMISSION_BTREE (CREATE_TIME,UPDATE_TIME,`TYPE`,OPERATION_ID,RESOURCE_ID,REMARK,STATUS,ID) VALUES
	 (1702282318591,1702282318591,'element',2,41,NULL,1,41),
	 (1702282318591,1702282318591,'element',2,42,NULL,1,42),
	 (1702282318591,1702282318591,'element',2,43,NULL,1,43),
	 (1702282318591,1702282318591,'element',2,44,NULL,1,44),
	 (1702282318591,1702282318591,'element',2,45,NULL,1,45),
	 (1702282318591,1702282318591,'element',2,46,NULL,1,46),
	 (1702282318591,1702282318591,'element',2,47,NULL,1,47),
	 (1702282318591,1702282318591,'element',2,48,NULL,1,48),
	 (1702282318591,1702282318591,'element',2,49,NULL,1,49),
	 (1702282318591,1702282318591,'element',2,50,NULL,1,50);
INSERT INTO myjarvex_btree.PERMISSION_BTREE (CREATE_TIME,UPDATE_TIME,`TYPE`,OPERATION_ID,RESOURCE_ID,REMARK,STATUS,ID) VALUES
	 (1702282318591,1702282318591,'element',2,51,NULL,1,51),
	 (1702282318591,1702282318591,'element',2,52,NULL,1,52),
	 (1702282318591,1702282318591,'element',2,53,NULL,1,53),
	 (1702282318591,1702282318591,'element',2,54,NULL,1,54),
	 (1702282318591,1702282318591,'element',2,55,NULL,1,55),
	 (1702282318591,1702282318591,'element',2,56,NULL,1,56),
	 (1702282318591,1702282318591,'element',2,57,NULL,1,57),
	 (1702282318591,1702282318591,'element',2,58,NULL,1,58),
	 (1702282318591,1702282318591,'element',2,59,NULL,1,59),
	 (1702282318591,1702282318591,'element',2,60,NULL,1,60);
INSERT INTO myjarvex_btree.PERMISSION_BTREE (CREATE_TIME,UPDATE_TIME,`TYPE`,OPERATION_ID,RESOURCE_ID,REMARK,STATUS,ID) VALUES
	 (1702282318591,1702282318591,'element',2,61,NULL,1,61),
	 (1702282318591,1702282318591,'element',2,62,NULL,1,62),
	 (1702282318591,1702282318591,'element',2,63,NULL,1,63),
	 (1702282318591,1702282318591,'element',2,64,NULL,1,64),
	 (1702282318591,1702282318591,'element',2,65,NULL,1,65),
	 (1702282318591,1702282318591,'element',2,66,NULL,1,66),
	 (1702282318591,1702282318591,'element',2,67,NULL,1,67),
	 (1702282318591,1702282318591,'element',2,68,NULL,1,68),
	 (1702282318591,1702282318591,'element',2,69,NULL,1,69),
	 (1702282318591,1702282318591,'element',2,70,NULL,1,70);
INSERT INTO myjarvex_btree.PERMISSION_BTREE (CREATE_TIME,UPDATE_TIME,`TYPE`,OPERATION_ID,RESOURCE_ID,REMARK,STATUS,ID) VALUES
	 (1702282318591,1702282318591,'element',2,71,NULL,1,71),
	 (1702282318591,1702282318591,'element',2,72,NULL,1,72),
	 (1702282318591,1702282318591,'element',2,73,NULL,1,73),
	 (1702282318591,1702282318591,'element',2,74,NULL,1,74),
	 (1702282318591,1702282318591,'element',2,75,NULL,1,75),
	 (1702282318591,1702282318591,'element',2,76,NULL,1,76),
	 (1702282318591,1702282318591,'element',2,77,NULL,1,77),
	 (1702282318591,1702282318591,'element',2,78,NULL,1,78);
INSERT INTO myjarvex_btree.PERMISSION_BTREE (CREATE_TIME,UPDATE_TIME,`TYPE`,OPERATION_ID,RESOURCE_ID,REMARK,STATUS,ID) VALUES
	 (1702282318591,1702282318591,'element',2,79,NULL,1,79);
DROP TABLE IF EXISTS myjarvex_btree.`PERMISSIONGROUP_BTREE`;
CREATE TABLE myjarvex_btree.`PERMISSIONGROUP_BTREE` (
	create_time BIGINT NOT NULL,
	update_time BIGINT NOT NULL,
	name VARCHAR(255) NOT NULL,
	label VARCHAR(255),
	remark VARCHAR(255),
	status INTEGER NOT NULL,
	id INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (id)
--	UNIQUE (name)
) engine=BTREE AUTO_INCREMENT = 1000 COMMENT='权限组信息表';
DROP TABLE IF EXISTS myjarvex_btree.`REL_PGROUP_PERMISSION_BTREE`;
CREATE TABLE myjarvex_btree.`REL_PGROUP_PERMISSION_BTREE` (
	create_time BIGINT NOT NULL,
	update_time BIGINT NOT NULL,
	pgroup_id INTEGER NOT NULL,
	permission_id INTEGER NOT NULL,
	PRIMARY KEY (pgroup_id, permission_id)
) engine=BTREE AUTO_INCREMENT = 1000 COMMENT='权限组与权限关联表';
INSERT INTO myjarvex_btree.`REL_PGROUP_PERMISSION_BTREE` (CREATE_TIME,UPDATE_TIME,PGROUP_ID,PERMISSION_ID) VALUES
	 (1703059064307,1703059064307,1,1),
	 (1703059064307,1703059064307,1,3),
	 (1703059064307,1703059064307,1,17),
	 (1703059064307,1703059064307,1,20),
	 (1703059064307,1703059064307,2,1),
	 (1703059064307,1703059064307,2,3),
	 (1703060468491,1703060468491,2,14),
	 (1703059064307,1703059064307,2,17),
	 (1703059064307,1703059064307,2,18),
	 (1703059064307,1703059064307,2,19);
INSERT INTO myjarvex_btree.`REL_PGROUP_PERMISSION_BTREE` (CREATE_TIME,UPDATE_TIME,PGROUP_ID,PERMISSION_ID) VALUES
	 (1703059064307,1703059064307,2,20),
	 (1703059064307,1703059064307,2,21),
	 (1703059064307,1703059064307,2,22),
	 (1703059064307,1703059064307,2,33),
	 (1703059064307,1703059064307,2,34),
	 (1703059064307,1703059064307,2,35),
	 (1703059064307,1703059064307,2,36),
	 (1703059064307,1703059064307,2,37),
	 (1703059064307,1703059064307,2,38),
	 (1703059064307,1703059064307,3,1);
INSERT INTO myjarvex_btree.`REL_PGROUP_PERMISSION_BTREE` (CREATE_TIME,UPDATE_TIME,PGROUP_ID,PERMISSION_ID) VALUES
	 (1703059064307,1703059064307,3,3),
	 (1703059064307,1703059064307,3,6),
	 (1703059064307,1703059064307,3,14),
	 (1703059064307,1703059064307,3,15),
	 (1703059064307,1703059064307,3,16),
	 (1703059064307,1703059064307,3,17),
	 (1703059064307,1703059064307,3,18),
	 (1703059064307,1703059064307,3,19),
	 (1703059064307,1703059064307,3,20),
	 (1703059064307,1703059064307,3,21);
INSERT INTO myjarvex_btree.`REL_PGROUP_PERMISSION_BTREE` (CREATE_TIME,UPDATE_TIME,PGROUP_ID,PERMISSION_ID) VALUES
	 (1703059064307,1703059064307,3,22),
	 (1703059064307,1703059064307,3,23),
	 (1703059064307,1703059064307,3,24),
	 (1703059064307,1703059064307,3,33),
	 (1703059064307,1703059064307,3,34),
	 (1703059064307,1703059064307,3,35),
	 (1703059064307,1703059064307,3,36),
	 (1703059064307,1703059064307,3,37),
	 (1703059064307,1703059064307,3,38),
	 (1703059064307,1703059064307,3,39);
INSERT INTO myjarvex_btree.`REL_PGROUP_PERMISSION_BTREE` (CREATE_TIME,UPDATE_TIME,PGROUP_ID,PERMISSION_ID) VALUES
	 (1703059064307,1703059064307,3,40),
	 (1703059064307,1703059064307,3,66),
	 (1703059064307,1703059064307,3,67),
	 (1703059064307,1703059064307,3,68),
	 (1703059064307,1703059064307,3,69),
	 (1703059064307,1703059064307,3,70),
	 (1703059064307,1703059064307,3,72),
	 (1703060140180,1703060140180,3,74),
	 (1703060140180,1703060140180,3,75),
	 (1703060140180,1703060140180,3,76);
INSERT INTO myjarvex_btree.`REL_PGROUP_PERMISSION_BTREE` (CREATE_TIME,UPDATE_TIME,PGROUP_ID,PERMISSION_ID) VALUES
	 (1703059064307,1703059064307,3,77),
	 (1703059064307,1703059064307,3,78),
	 (1703059064307,1703059064307,3,79);
DROP TABLE IF EXISTS myjarvex_btree.`REL_ROLE_PERMISSION_BTREE`;
CREATE TABLE myjarvex_btree.`REL_ROLE_PERMISSION_BTREE` (
  `role_id` int NOT NULL,
  `permission_id` int NOT NULL,
  `create_time` bigint NOT NULL,
  `update_time` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `ix_rel_role_permission_role_id` (`role_id`)
) engine=BTREE AUTO_INCREMENT = 1000 COMMENT='角色权限关联表';
INSERT INTO myjarvex_btree.REL_ROLE_PERMISSION_BTREE (ROLE_ID,PERMISSION_ID,CREATE_TIME,UPDATE_TIME) VALUES
	 (1,1,1702452709647,1702452709647),
	 (1,2,1702452709647,1702452709647),
	 (1,3,1702452709647,1702452709647),
	 (1,4,1702452709647,1702452709647),
	 (1,5,1702452709647,1702452709647),
	 (1,6,1702452709647,1702452709647),
	 (1,7,1702452709647,1702452709647),
	 (1,8,1702452709647,1702452709647),
	 (1,9,1702452709647,1702452709647),
	 (1,10,1702452709647,1702452709647);
INSERT INTO myjarvex_btree.REL_ROLE_PERMISSION_BTREE (ROLE_ID,PERMISSION_ID,CREATE_TIME,UPDATE_TIME) VALUES
	 (1,11,1702452709647,1702452709647),
	 (1,12,1702452709647,1702452709647),
	 (1,13,1702452709647,1702452709647),
	 (1,14,1702452709647,1702452709647),
	 (1,15,1702452709647,1702452709647),
	 (1,16,1702452709647,1702452709647),
	 (1,17,1702452709647,1702452709647),
	 (1,18,1702452709647,1702452709647),
	 (1,19,1702452709647,1702452709647),
	 (1,20,1702452709647,1702452709647);
INSERT INTO myjarvex_btree.REL_ROLE_PERMISSION_BTREE (ROLE_ID,PERMISSION_ID,CREATE_TIME,UPDATE_TIME) VALUES
	 (1,21,1702452709647,1702452709647),
	 (1,22,1702452709647,1702452709647),
	 (1,23,1702452709647,1702452709647),
	 (1,24,1702452709647,1702452709647),
	 (1,25,1702452709647,1702452709647),
	 (1,26,1702452709647,1702452709647),
	 (1,27,1702452709647,1702452709647),
	 (1,28,1702452709647,1702452709647),
	 (1,29,1702452709647,1702452709647),
	 (1,30,1702452709647,1702452709647);
INSERT INTO myjarvex_btree.REL_ROLE_PERMISSION_BTREE (ROLE_ID,PERMISSION_ID,CREATE_TIME,UPDATE_TIME) VALUES
	 (1,31,1702452709647,1702452709647),
	 (1,32,1702452709647,1702452709647),
	 (1,33,1702452709647,1702452709647),
	 (1,34,1702452709647,1702452709647),
	 (1,35,1702452709647,1702452709647),
	 (1,36,1702452709647,1702452709647),
	 (1,37,1702452709647,1702452709647),
	 (1,38,1702452709647,1702452709647),
	 (1,39,1702452709647,1702452709647),
	 (1,40,1702452709647,1702452709647);
INSERT INTO myjarvex_btree.REL_ROLE_PERMISSION_BTREE (ROLE_ID,PERMISSION_ID,CREATE_TIME,UPDATE_TIME) VALUES
	 (1,41,1702452709647,1702452709647),
	 (1,42,1702452709647,1702452709647),
	 (1,43,1702452709647,1702452709647),
	 (1,44,1702452709647,1702452709647),
	 (1,45,1702452709647,1702452709647),
	 (1,46,1702452709647,1702452709647),
	 (1,47,1702452709647,1702452709647),
	 (1,48,1702452709647,1702452709647),
	 (1,49,1702452709647,1702452709647),
	 (1,50,1702452709647,1702452709647);
INSERT INTO myjarvex_btree.REL_ROLE_PERMISSION_BTREE (ROLE_ID,PERMISSION_ID,CREATE_TIME,UPDATE_TIME) VALUES
	 (1,51,1702452709647,1702452709647),
	 (1,52,1702452709647,1702452709647),
	 (1,53,1702452709647,1702452709647),
	 (1,54,1702452709647,1702452709647),
	 (1,55,1702452709647,1702452709647),
	 (1,56,1702452709647,1702452709647),
	 (1,57,1702452709647,1702452709647),
	 (1,58,1702452709647,1702452709647),
	 (1,59,1702452709647,1702452709647),
	 (1,60,1702452709647,1702452709647);
INSERT INTO myjarvex_btree.REL_ROLE_PERMISSION_BTREE (ROLE_ID,PERMISSION_ID,CREATE_TIME,UPDATE_TIME) VALUES
	 (1,61,1702452709647,1702452709647),
	 (1,62,1702452709647,1702452709647),
	 (1,63,1702452709647,1702452709647),
	 (1,64,1702452709647,1702452709647),
	 (1,65,1702452709647,1702452709647),
	 (1,66,1702452709647,1702452709647),
	 (1,67,1702452709647,1702452709647),
	 (1,68,1702452709647,1702452709647),
	 (1,69,1702452709647,1702452709647),
	 (1,70,1702452709647,1702452709647);
INSERT INTO myjarvex_btree.REL_ROLE_PERMISSION_BTREE (ROLE_ID,PERMISSION_ID,CREATE_TIME,UPDATE_TIME) VALUES
	 (1,71,1702452709647,1702452709647),
	 (1,72,1702452709647,1702452709647),
	 (1,73,1702452709647,1702452709647),
	 (1,74,1702452709647,1702452709647),
	 (1,75,1702452709647,1702452709647),
	 (1,76,1702452709647,1702452709647),
	 (1,77,1702452709647,1702452709647),
	 (1,78,1702452709647,1702452709647);
INSERT INTO myjarvex_btree.REL_ROLE_PERMISSION_BTREE (ROLE_ID,PERMISSION_ID,CREATE_TIME,UPDATE_TIME) VALUES
	 (1,79,1702452709647,1702452709647);
DROP TABLE IF EXISTS myjarvex_btree.`REL_ROLE_PGROUP_BTREE`;
CREATE TABLE myjarvex_btree.`REL_ROLE_PGROUP_BTREE` (
	`create_time` bigint NOT NULL,
    `update_time` bigint NOT NULL,
	role_id INTEGER NOT NULL,
	pgroup_id INTEGER NOT NULL,
	PRIMARY KEY (role_id, pgroup_id)
) engine=BTREE AUTO_INCREMENT = 1000 COMMENT='角色与权限组关联表';
INSERT INTO myjarvex_btree.REL_ROLE_PGROUP_BTREE (CREATE_TIME,UPDATE_TIME,ROLE_ID,PGROUP_ID) VALUES
	 (1703059064319,1703059064319,1,1),
	 (1703059064319,1703059064319,2,2),
	 (1703059681913,1703059681913,3,1),
	 (1703059681913,1703059681913,4,2),
	 (1703059681913,1703059681913,5,3);
DROP TABLE IF EXISTS myjarvex_btree.`REL_USER_PERMISSION_BTREE`;
CREATE TABLE myjarvex_btree.`REL_USER_PERMISSION_BTREE` (
	create_time BIGINT NOT NULL,
	update_time BIGINT NOT NULL,
	user_id INTEGER NOT NULL,
	permission_id INTEGER NOT NULL,
	PRIMARY KEY (user_id, permission_id)
) engine=BTREE AUTO_INCREMENT = 1000 COMMENT='用户与权限关联表';
DROP TABLE IF EXISTS myjarvex_btree.`REL_USER_ROLE_BTREE`;
CREATE TABLE myjarvex_btree.`REL_USER_ROLE_BTREE` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  `create_time` bigint NOT NULL,
  `update_time` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) engine=BTREE AUTO_INCREMENT = 1000 COMMENT='';
INSERT INTO myjarvex_btree.REL_USER_ROLE_BTREE (user_id,role_id,create_time,update_time)
values
(1,1,1702282318591,1702282318591);
DROP TABLE IF EXISTS myjarvex_btree.`REL_USERGROUP_ROLE_BTREE`;
CREATE TABLE myjarvex_btree.`REL_USERGROUP_ROLE_BTREE` (
	create_time BIGINT NOT NULL,
	update_time BIGINT NOT NULL,
	usergroup_id INTEGER NOT NULL,
	role_id INTEGER NOT NULL,
	PRIMARY KEY (usergroup_id, role_id)
) engine=BTREE AUTO_INCREMENT = 1000 COMMENT='用户组与角色关联表';
INSERT INTO myjarvex_btree.REL_USERGROUP_ROLE_BTREE (CREATE_TIME,UPDATE_TIME,USERGROUP_ID,ROLE_ID) VALUES
	 (1703060370502,1703060370502,3,4),
	 (1703060370502,1703060370502,4,5);
DROP TABLE IF EXISTS myjarvex_btree.`REL_USERGROUP_USER_BTREE`;
CREATE TABLE myjarvex_btree.`REL_USERGROUP_USER_BTREE` (
	create_time BIGINT NOT NULL,
	update_time BIGINT NOT NULL,
	usergroup_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	PRIMARY KEY (usergroup_id, user_id)
) engine=BTREE AUTO_INCREMENT = 1000 COMMENT='用户组与用户关联表';
INSERT INTO myjarvex_btree.REL_USERGROUP_USER_BTREE (CREATE_TIME,UPDATE_TIME,USERGROUP_ID,USER_ID) VALUES
	 (1703060370505,1703060370505,1,4),
	 (1703060370505,1703060370505,1,5),
	 (1703060370505,1703060370505,1,6),
	 (1703060370505,1703060370505,3,5),
	 (1703060370505,1703060370505,3,6),
	 (1703060370505,1703060370505,4,6);
DROP TABLE IF EXISTS myjarvex_btree.`RESOURCE_BTREE`;
CREATE TABLE myjarvex_btree.`RESOURCE_BTREE` (
	create_time BIGINT NOT NULL,
	update_time BIGINT NOT NULL,
	id INTEGER NOT NULL AUTO_INCREMENT,
	parent_id INTEGER,
	name VARCHAR(255),
	type VARCHAR(255) NOT NULL,
	rel_id INTEGER,
	`key` VARCHAR(255),
	remark VARCHAR(255),
	status INTEGER NOT NULL,
	PRIMARY KEY (id)
--	UNIQUE (id, parent_id),
--	UNIQUE (name),
--	UNIQUE (`key`)
) engine=BTREE AUTO_INCREMENT = 1000 COMMENT='资源信息表';
INSERT INTO myjarvex_btree.RESOURCE_BTREE (CREATE_TIME,UPDATE_TIME,ID,PARENT_ID,NAME,`TYPE`,REL_ID,`KEY`,REMARK,STATUS) VALUES
	 (1702282318591,1702282318591,1,0,'智问智答','menu',NULL,'chat',NULL,1),
	 (1702282318591,1702282318591,2,0,'企业应用','menu',NULL,'app',NULL,1),
	 (1702282318591,1702282318591,3,0,'数据管理','menu',NULL,'data',NULL,1),
	 (1702282318591,1702282318591,4,0,'模型微调','menu',NULL,'model',NULL,1),
	 (1702282318591,1702282318591,5,0,'构建工作流','menu',NULL,'buildApp',NULL,1),
	 (1702282318591,1702282318591,6,0,'系统管理','menu',NULL,'system',NULL,1),
	 (1702282318591,1702282318591,7,1,'创意抽象资源创建','element',NULL,'creativity_publish',NULL,1),
	 (1702282318591,1702282318591,8,1,'编辑创意','element',NULL,'creativity_edit',NULL,1),
	 (1702282318591,1702282318591,9,1,'删除创意','element',NULL,'creativity_delete',NULL,1),
	 (1702282318591,1702282318591,10,2,'新建应用','element',NULL,'app_create',NULL,1);
INSERT INTO myjarvex_btree.RESOURCE_BTREE (CREATE_TIME,UPDATE_TIME,ID,PARENT_ID,NAME,`TYPE`,REL_ID,`KEY`,REMARK,STATUS) VALUES
	 (1702282318591,1702282318591,11,2,'编辑应用','element',NULL,'app_edit',NULL,1),
	 (1702282318591,1702282318591,12,2,'删除应用','element',NULL,'app_delete',NULL,1),
	 (1702282318591,1702282318591,13,2,'上线应用','element',NULL,'app_pulish',NULL,1),
	 (1702282318591,1702282318591,14,3,'数据集抽象资源创建','element',NULL,'dataset_publish',NULL,1),
	 (1702282318591,1702282318591,15,3,'数据集-编辑','element',NULL,'dataset_edit',NULL,1),
	 (1702282318591,1702282318591,16,3,'数据集-删除','element',NULL,'dataset_delete',NULL,1),
	 (1702282318591,1702282318591,17,3,'数据集文件-列表','element',NULL,'dataset_file_list',NULL,1),
	 (1702282318591,1702282318591,18,3,'数据集文件-上传','element',NULL,'dataset_file_upload',NULL,1),
	 (1702282318591,1702282318591,19,3,'数据集文件-分段设置','element',NULL,'dataset_file_section',NULL,1),
	 (1702282318591,1702282318591,20,3,'数据集文件-预览','element',NULL,'dataset_file_preview',NULL,1);
INSERT INTO myjarvex_btree.RESOURCE_BTREE (CREATE_TIME,UPDATE_TIME,ID,PARENT_ID,NAME,`TYPE`,REL_ID,`KEY`,REMARK,STATUS) VALUES
	 (1702282318591,1702282318591,21,3,'数据集文件-删除','element',NULL,'dataset_file_delete',NULL,1),
	 (1702282318591,1702282318591,22,3,'数据集文件-提交','element',NULL,'dataset_file_submit',NULL,1),
	 (1702282318591,1702282318591,23,3,'数据集文件-通过','element',NULL,'dataset_file_pass',NULL,1),
	 (1702282318591,1702282318591,24,3,'数据集文件-打回','element',NULL,'dataset_file_refuse',NULL,1),
	 (1702282318591,1702282318591,25,3,'微调数据集','element',NULL,'dataset_qa_list',NULL,1),
	 (1702282318591,1702282318591,26,3,'微调数据集-新增文件','element',NULL,'dataset_qa_upload',NULL,1),
	 (1702282318591,1702282318591,27,3,'微调数据集-分段设置','element',NULL,'dataset_qa_section',NULL,1),
	 (1702282318591,1702282318591,28,3,'微调数据集-预览','element',NULL,'dataset_qa_preview',NULL,1),
	 (1702282318591,1702282318591,29,3,'微调数据集-删除','element',NULL,'dataset_qa_delete',NULL,1),
	 (1702282318591,1702282318591,30,3,'微调数据集-生成QA','element',NULL,'dataset_qa_create',NULL,1);
INSERT INTO myjarvex_btree.RESOURCE_BTREE (CREATE_TIME,UPDATE_TIME,ID,PARENT_ID,NAME,`TYPE`,REL_ID,`KEY`,REMARK,STATUS) VALUES
	 (1702282318591,1702282318591,31,3,'微调数据集-查看QA','element',NULL,'dataset_qa_detail',NULL,1),
	 (1702282318591,1702282318591,32,3,'微调数据集-QA下载','element',NULL,'dataset_qa_download',NULL,1),
	 (1702282318591,1702282318591,33,3,'数据集专家经验','element',NULL,'dataset_expertise_list',NULL,1),
	 (1702282318591,1702282318591,34,3,'数据集专家经验-新增','element',NULL,'dataset_expertise_add',NULL,1),
	 (1702282318591,1702282318591,35,3,'数据集专家经验-编辑','element',NULL,'dataset_expertise_edit',NULL,1),
	 (1702282318591,1702282318591,36,3,'数据集专家经验-提交','element',NULL,'dataset_expertise_submit',NULL,1),
	 (1702282318591,1702282318591,37,3,'数据集专家经验-预览','element',NULL,'dataset_expertise_detail',NULL,1),
	 (1702282318591,1702282318591,38,3,'数据集专家经验-删除','element',NULL,'dataset_expertise_delete',NULL,1),
	 (1702282318591,1702282318591,39,3,'数据集专家经验-通过','element',NULL,'dataset_expertise_pass',NULL,1),
	 (1702282318591,1702282318591,40,3,'数据集专家经验-打回','element',NULL,'dataset_expertise_fuse',NULL,1);
INSERT INTO myjarvex_btree.RESOURCE_BTREE (CREATE_TIME,UPDATE_TIME,ID,PARENT_ID,NAME,`TYPE`,REL_ID,`KEY`,REMARK,STATUS) VALUES
	 (1702282318591,1702282318591,41,4,'新建模型','element',NULL,'model_create',NULL,1),
	 (1702282318591,1702282318591,42,4,'编辑模型','element',NULL,'model_edit',NULL,1),
	 (1702282318591,1702282318591,43,4,'删除模型','element',NULL,'model_delete',NULL,1),
	 (1702282318591,1702282318591,44,4,'微调模型','element',NULL,'model_finetune',NULL,1),
	 (1702282318591,1702282318591,45,5,'创建工作流','element',NULL,'flow_create',NULL,1),
	 (1702282318591,1702282318591,46,5,'编辑工作流','element',NULL,'flow_edit',NULL,1),
	 (1702282318591,1702282318591,47,5,'删除工作流','element',NULL,'flow_delete',NULL,1),
	 (1702282318591,1702282318591,48,6,'资源管理','element',NULL,'resource_manage',NULL,1),
	 (1702282318591,1702282318591,49,6,'新建资源','element',NULL,'resource_create',NULL,1),
	 (1702282318591,1702282318591,50,6,'编辑资源','element',NULL,'resource_edit',NULL,1);
INSERT INTO myjarvex_btree.RESOURCE_BTREE (CREATE_TIME,UPDATE_TIME,ID,PARENT_ID,NAME,`TYPE`,REL_ID,`KEY`,REMARK,STATUS) VALUES
	 (1702282318591,1702282318591,51,6,'删除资源','element',NULL,'resource_delete',NULL,1),
	 (1702282318591,1702282318591,52,6,'权限管理','element',NULL,'permission_manage',NULL,1),
	 (1702282318591,1702282318591,53,6,'编辑权限','element',NULL,'permission_edit',NULL,1),
	 (1702282318591,1702282318591,54,6,'操作管理','element',NULL,'operation_manage',NULL,1),
	 (1702282318591,1702282318591,55,6,'新增操作','element',NULL,'operation_create',NULL,1),
	 (1702282318591,1702282318591,56,6,'编辑操作','element',NULL,'operation_edit',NULL,1),
	 (1702282318591,1702282318591,57,6,'删除操作','element',NULL,'operation_delete',NULL,1),
	 (1702282318591,1702282318591,58,6,'权限组管理','element',NULL,'pgroup_manage',NULL,1),
	 (1702282318591,1702282318591,59,6,'新建权限组','element',NULL,'pgroup_create',NULL,1),
	 (1702282318591,1702282318591,60,6,'编辑权限组','element',NULL,'pgroup_edit',NULL,1);
INSERT INTO myjarvex_btree.RESOURCE_BTREE (CREATE_TIME,UPDATE_TIME,ID,PARENT_ID,NAME,`TYPE`,REL_ID,`KEY`,REMARK,STATUS) VALUES
	 (1702282318591,1702282318591,61,6,'删除权限组','element',NULL,'pgroup_delete',NULL,1),
	 (1702282318591,1702282318591,62,6,'角色管理','element',NULL,'role_manage',NULL,1),
	 (1702282318591,1702282318591,63,6,'新增角色','element',NULL,'role_create',NULL,1),
	 (1702282318591,1702282318591,64,6,'编辑角色','element',NULL,'role_edit',NULL,1),
	 (1702282318591,1702282318591,65,6,'删除角色','element',NULL,'role_delete',NULL,1),
	 (1702282318591,1702282318591,66,6,'用户管理','element',NULL,'user_manage',NULL,1),
	 (1702282318591,1702282318591,67,6,'新增用户','element',NULL,'user_create',NULL,1),
	 (1702282318591,1702282318591,68,6,'编辑用户','element',NULL,'user_edit',NULL,1),
	 (1702282318591,1702282318591,69,6,'删除用户','element',NULL,'user_delete',NULL,1),
	 (1702282318591,1702282318591,70,6,'团队管理','element',NULL,'ugroup_manage',NULL,1);
INSERT INTO myjarvex_btree.RESOURCE_BTREE (CREATE_TIME,UPDATE_TIME,ID,PARENT_ID,NAME,`TYPE`,REL_ID,`KEY`,REMARK,STATUS) VALUES
	 (1702282318591,1702282318591,71,6,'新建团队','element',NULL,'ugroup_create',NULL,1),
	 (1702282318591,1702282318591,72,6,'编辑团队','element',NULL,'ugroup_edit',NULL,1),
	 (1702282318591,1702282318591,73,6,'删除团队','element',NULL,'ugroup_delete',NULL,1),
	 (1702282318591,1702282318591,74,6,'成员管理','element',NULL,'member_manage',NULL,1),
	 (1702282318591,1702282318591,75,6,'新增成员','element',NULL,'member_create',NULL,1),
	 (1702282318591,1702282318591,76,6,'踢出成员','element',NULL,'member_dismiss',NULL,1),
	 (1702282318591,1702282318591,77,6,'聊天历史','element',NULL,'chat_history',NULL,1),
	 (1702282318591,1702282318591,78,6,'用户反馈','element',NULL,'user_suggestions',NULL,1);
INSERT INTO myjarvex_btree.RESOURCE_BTREE (CREATE_TIME,UPDATE_TIME,ID,PARENT_ID,NAME,`TYPE`,REL_ID,`KEY`,REMARK,STATUS) VALUES
	 (1702282318591,1702282318591,79,3,'数据集命中测试','element',NULL,'dataset_hit',NULL,1);
DROP TABLE IF EXISTS myjarvex_btree.`ROLE_BTREE`;
CREATE TABLE myjarvex_btree.`ROLE_BTREE` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `nick_name` varchar(255)  DEFAULT NULL,
  `label` varchar(255) NOT NULL,
  `status` int NOT NULL,
  `create_time` bigint NOT NULL,
  `update_time` bigint NOT NULL,
  PRIMARY KEY (`id`)
) engine=BTREE AUTO_INCREMENT = 1000 COMMENT='角色表';
INSERT INTO myjarvex_btree.`role_BTREE` (ID,NAME,NICK_NAME,LABEL,STATUS,CREATE_TIME,UPDATE_TIME) VALUES
	 (1,'role-admin','超级管理员','system',1,1701918748806,1702452328126),
	 (2,'role-default','默认角色','system',1,1701918748806,1702452727367),
	 (3,'baowu-user','宝武普通员工','team',1,1701918748806,1702452727367),
	 (4,'baowu-expert','宝武专家','team',1,1701918748806,1702452727367),
	 (5,'baowu-admin','宝武管理员','team',1,1701918748806,1702452727367);
DROP TABLE IF EXISTS myjarvex_btree.`USER_BTREE`;
CREATE TABLE myjarvex_btree.`USER_BTREE` (
	create_time BIGINT NOT NULL,
	update_time BIGINT NOT NULL,
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	nick_name VARCHAR(255),
	phone VARCHAR(255),
	email VARCHAR(255),
	status INTEGER NOT NULL,
	remark VARCHAR(255),
	expire_time INTEGER,
	PRIMARY KEY (id)
--	UNIQUE (name)
) engine=BTREE AUTO_INCREMENT = 1000 COMMENT='用户表';
INSERT INTO myjarvex_btree.`USER_BTREE` (CREATE_TIME,UPDATE_TIME,ID,NAME,PASSWORD,NICK_NAME,PHONE,EMAIL,STATUS,REMARK,EXPIRE_TIME) VALUES
	 (1701429522047,1701429522047,1,'admin','$2b$12$Ja/WHlzqIJKJmeQ3/8ArOeEUz73eE6q9TJRGiqx.qBNC3Vo/FkAV6','超级管理员','','myjarvex_btree@zetyun.com',1,'系统默认超级管理员',NULL),
	 (1701429522047,1701429522047,2,'manager','$2b$12$vrRjeO4g30btUNP41QYS2eWW3orp2HjdRrCdFvSX/BhfG7yTWT5Em','系统普通管理员','','myjarvex_btree@zetyun.com',1,'管理员',NULL),
	 (1701429522047,1701429522047,3,'guest','$2b$12$MY/yxv5i8hoZquBl.dka8ep5JVDUd1xkQ.EtVbVr72VjMPFKzHeLi','访客','','myjarvex_btree@zetyun.com',1,'系统默认访客',NULL),
	 (1701429522047,1701429522047,4,'baowu-user-1','$2b$12$6La83TRaPNwbn/EnBeU24OFRdqIKDgtascwumq81ZQFheJk42dlPC','普通员工','','',1,'宝武普通员工',NULL),
	 (1701429522047,1701429522047,5,'baowu-expert-1','$2b$12$vUky8C4Asidr1MbTTYPX9Olmd/b8g/lDCafAJcb3Q3EllTLuAB8Su','专家','','',1,'宝武专家',NULL),
	 (1701429522047,1701429522047,6,'baowu-admin-1','$2b$12$Kkm6sURXFcNJTlSdfcknYuB1K/DNi6PYPXz8NkntYHXkNucCcKe6.','管理员','','',1,'宝武管理员',NULL);
DROP TABLE IF EXISTS myjarvex_btree.`USER_BASE_BTREE`;
CREATE TABLE myjarvex_btree.`USER_BASE_BTREE` (
	user_name VARCHAR(255),
	email VARCHAR(255),
	phone_number VARCHAR(255),
	role_id VARCHAR(255),
	department_id VARCHAR(255),
	is_mark VARCHAR(255),
	leavl_id VARCHAR(255),
	create_time VARCHAR(255),
	update_time VARCHAR(255),
	user_id INTEGER NOT NULL AUTO_INCREMENT,
	passwords VARCHAR(255),
	PRIMARY KEY (user_id)
) engine=BTREE AUTO_INCREMENT = 1000;
DROP TABLE IF EXISTS myjarvex_btree.`USERGROUP_BTREE`;
CREATE TABLE myjarvex_btree.`USERGROUP_BTREE` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255)  NOT NULL,
  `label` varchar(255)  NOT NULL,
  `remark` varchar(255)  DEFAULT NULL,
  `status` int NOT NULL,
  `create_time` bigint NOT NULL,
  `update_time` bigint NOT NULL,
  PRIMARY KEY (`id`)
) engine=BTREE AUTO_INCREMENT = 1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户组';
INSERT INTO myjarvex_btree.`USERGROUP_BTREE` (ID,NAME,LABEL,REMARK,STATUS,CREATE_TIME,UPDATE_TIME) VALUES
	 (1,'宝武普通员工团队','default',NULL,1,1703060140229,1703060140229),
	 (3,'宝武专家团队','default',NULL,1,1703060370536,1703060370536),
	 (4,'宝武管理员团队','default',NULL,1,1703060370536,1703060370536);
DROP TABLE IF EXISTS myjarvex_btree.`VECTOR_DATA_BTREE`;
CREATE TABLE myjarvex_btree.`VECTOR_DATA_BTREE` (
	vector_name VARCHAR(255),
	user_id VARCHAR(255),
	dataset_id VARCHAR(255),
	vector_history VARCHAR(255),
	create_time VARCHAR(255),
	is_mark VARCHAR(255),
	vector_id INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (vector_id)
) engine=BTREE AUTO_INCREMENT = 1000