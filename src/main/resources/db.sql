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

create table tbl_relic
(
	id bigint AUTO_INCREMENT,
	bh varchar(10),
	name varchar(32),
	coordinate varchar(32),
	desc text,
	category varchar(10),
	s_pic varchar(255),
	pic varchar(4000),
	scenic_spots_id varchar(64),
	update_time bigint;
	extra text,
	primary key (id, scenic_spots_id)
) ENGINE = INNODB

DELIMITER $$
DROP PROCEDURE IF EXISTS get_insert_id $$
CREATE PROCEDURE get_insert_id(IN bh VARCHAR(10),IN NAME VARCHAR(32),IN coordinate VARCHAR(32),IN descr TEXT,IN category VARCHAR(10),IN s_pic VARCHAR(255),
IN pic TEXT,IN scenic_spots_id VARCHAR(64),IN update_time BIGINT(20),IN extra TEXT)
BEGIN
  INSERT INTO tbl_relic (bh, NAME, coordinate,descr,category,s_pic,pic,scenic_spots_id,update_time,extra)
    VALUES (bh, NAME, coordinate,descr,category,s_pic,pic,scenic_spots_id,update_time,extra);
  SELECT MAX(id) FROM tbl_relic;
END $$
DELIMITER ;
