create table tbl_users
(
	id varchar(64) primary key,
	user_name varchar(32) not null,
	passpharse varchar(32),
	login_time bigint,
	role_type int default 0,
	status int default 0,
	extra_info varchar(2000),
	parent_id varchar(64)
) ENGINE = INNODB

create table tbl_scenic_spots
(
	id varchar(64) primary key,
	name varchar(32) not null,
	owner varchar(64) not null,
	park_id varchar(64),
	info text,
	update_time bigint,
	extra text
) ENGINE = INNODB