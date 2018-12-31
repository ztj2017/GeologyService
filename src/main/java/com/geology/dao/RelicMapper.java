package com.geology.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.geology.base.BaseMapper;
import com.geology.model.Relic;

@Repository
public interface RelicMapper extends BaseMapper
{
	Long insertRelic(Relic relic);
	
	List<Relic> getRelicByConditions(Map<String, Object> queryMap);
	
	Relic load(@Param("id")Long id,@Param("spotId")String spotId);
	
	void update(Relic relic);
	
	Integer count(Map<String, Object> queryMap);
}
