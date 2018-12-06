create table tbl_users
(
	id varchar(32) primary key,
	user_name varchar(32) not null,
	passpharse varchar(32),
	login_time bigint,
	extra_info varchar(2000)
)