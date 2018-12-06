package com.geology.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.geology.base.BaseMapper;
import com.geology.model.User;

@Repository
public interface UserMapper extends BaseMapper
{
	List<User> queryByMap(Map<String, Object> map);
}
