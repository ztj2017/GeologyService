package com.geology.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.geology.base.BaseMapper;
import com.geology.model.Spots;

@Repository
public interface SpotsMapper extends BaseMapper
{
	void insertSpot(Spots spots);

	List<Spots> getSpotsByConditions(Map<String, Object> queryMap);
	
	Spots load(String id);
}
