package com.geology.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geology.dao.UserMapper;
import com.geology.model.User;

@Service
public class UserService
{

	@Autowired
	private UserMapper userDao;

	public List<User> checkUser(User user)
	{
		Map<String, Object> conditionMap = new HashMap<String, Object>();

		conditionMap.put("userName", user.getUserName());

		conditionMap.put("passPharse", user.getPassPharse());

		return userDao.queryByMap(conditionMap);
	}
	
	public User getUserById(String id)
	{
		Map<String, Object> conditionMap = new HashMap<String, Object>();

		conditionMap.put("id", id);
		List<User> list =  userDao.queryByMap(conditionMap);
		if(list.size() > 0)
		{
			return list.get(0);
		}
		return new User();
	}
	
}
