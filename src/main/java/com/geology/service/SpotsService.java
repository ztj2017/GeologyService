package com.geology.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.geology.dao.SpotsMapper;
import com.geology.model.Spots;

@Service
public class SpotsService
{
	@Autowired
	private SpotsMapper spotDao;

	public List<Spots> queryByConditions(Spots s)
	{
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		if (s.getOwner() != null)
		{
			conditionMap.put("owner", s.getOwner());
		}
		if (s.getId() != null)
		{
			conditionMap.put("id", s.getId());
		}
		return spotDao.getSpotsByConditions(conditionMap);
	}

	public Spots load(String id)
	{
		return spotDao.load(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addSpot(Spots spots)
	{
		spotDao.insertSpot(spots);
	}
}
