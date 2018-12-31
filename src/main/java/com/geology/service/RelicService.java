package com.geology.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.geology.dao.RelicMapper;
import com.geology.model.Page;
import com.geology.model.Relic;
import com.geology.utils.DateUtils;

@Service
public class RelicService
{
	private static final int pageSize = 8;

	@Autowired
	private RelicMapper relicDao;

	@Transactional(propagation = Propagation.REQUIRED)
	public Long insertRelic(Relic relic)
	{
		return relicDao.insertRelic(relic);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateRelic(Relic relic)
	{
		relicDao.update(relic);
	}

	public Relic getRelicById(String spotId, String id)
	{
		return relicDao.load(Long.parseLong(id), spotId);
	}

	public JSONObject getRelicByConditions(String spotId, int thisPage)
	{
		Page page = new Page();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("scenicSpotsId", spotId);
		if (thisPage < 1)
		{
			thisPage = 1;
		}
		int rowsNum = relicDao.count(queryMap);
		System.out.println("总行数=" + rowsNum);
		if (rowsNum < 1)
			return new JSONObject();
		int maxPage = (rowsNum - 1 + pageSize) / pageSize;
		page.setThisPage(thisPage);
		page.setPageSize(pageSize);
		page.setMaxPage(maxPage);
		page.setRowsNum(rowsNum);
		if (thisPage > maxPage)
		{
			page.setThisPage(maxPage);
		}
		int begin = (page.getThisPage() - 1) * pageSize;
		queryMap.put("begin", begin);
		queryMap.put("end", pageSize);
		List<Relic> relics =  relicDao.getRelicByConditions(queryMap);
		for(Relic relic : relics){
			relic.setUpdateTime(DateUtils.stampToDate(relic.getUpdateTime()));
		}
		JSONObject resJson = new JSONObject();
		resJson.put("relics", relics);
		resJson.put("page", page);
		return resJson;
	}

}
