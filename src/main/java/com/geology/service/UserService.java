package com.geology.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geology.dao.UserMapper;
import com.geology.model.User;

@Service
public class UserService
{

	@Autowired
	private UserMapper userDao;
	
	public List<User> checkUser()
	{
		return userDao.queryByMap(new HashMap<String, Object>());
	}
}