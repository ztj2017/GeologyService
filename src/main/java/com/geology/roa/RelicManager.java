package com.geology.roa;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geology.model.Relic;
import com.geology.service.RelicService;
import com.geology.utils.DateUtils;
import com.geology.utils.ErrorInfo;

@Path("/v1/relic")
public class RelicManager
{
	private static final Logger LOGGER = Logger.getLogger(RelicManager.class);

	@Autowired
	private RelicService relicServ;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRelic(@Context HttpServletRequest request)
	{
		LOGGER.info("add relic begin.");
		String input = null;
		Relic relic = null;
		try
		{
			input = IOUtils.toString(request.getInputStream());
			relic = JSON.parseObject(input, Relic.class);
		} catch (Exception e)
		{
			LOGGER.error("input stream to object exception.");
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode("E0001");
			errorInfo.setCasue("input error.");
			return Response.status(400).entity(JSON.toJSONString(errorInfo)).build();
		}
		relic.setUpdateTime(System.currentTimeMillis() + "");
		relic.setsPic("/upload/nopic.png");
		Long relicId = relicServ.insertRelic(relic);
		JSONObject json = new JSONObject();
		json.put("id", relicId);
		return Response.status(200).entity(json).build();
	}

	// 更新遗址
	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRelic(@Context HttpServletRequest request, @PathParam("id") String id)
	{
		LOGGER.info("update relic begin.");
		String input = null;
		Relic relic = null;
		try
		{
			input = IOUtils.toString(request.getInputStream());
			relic = JSON.parseObject(input, Relic.class);
		} catch (Exception e)
		{
			LOGGER.error("input stream to object exception.");
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode("E0001");
			errorInfo.setCasue("input error.");
			return Response.status(400).entity(JSON.toJSONString(errorInfo)).build();
		}
		relic.setUpdateTime(System.currentTimeMillis() + "");
		relic.setId(Long.parseLong(id));
		relicServ.updateRelic(relic);
		try
		{
			if (relic.getDeleteImg() != null)
			{
				LOGGER.info("delete image file...");
				String uploadRoot = getUploadPath(request.getSession().getServletContext().getRealPath(""));
				JSONArray array = JSONArray.parseArray(relic.getDeleteImg());
				for (int i = 0; i < array.size(); i++)
				{
					String imgPath = array.getString(0);
					File imageFile = new File(uploadRoot + imgPath);
					if (imageFile.exists())
					{
						imageFile.delete();
						LOGGER.warn("delete image file = " + imageFile.getAbsolutePath());
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return Response.status(200).build();
	}

	// 查询单个遗址
	@GET
	@Path("/{spotId}/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRelic(@Context HttpServletRequest request, @PathParam("spotId") String spotId,
			@PathParam("id") String id)
	{
		Relic relic = relicServ.getRelicById(spotId, id);
		if (relic == null)
		{
			return Response.status(400).build();
		}
		relic.setUpdateTime(DateUtils.stampToDate(relic.getUpdateTime()));
		return Response.ok().entity(JSON.toJSONString(relic)).build();
	}

	// 查询景区下遗址
	@GET
	@Path("/{spotId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRelicBySpot(@Context HttpServletRequest request, @PathParam("spotId") String spotId,
			@QueryParam("page") int page)
	{

		JSONObject json = relicServ.getRelicByConditions(spotId, page);
		return Response.ok().entity(json).build();
	}

	private String getUploadPath(String ss)
	{
		int index = ss.indexOf("webapps");
		return ss.substring(0, index + "webapps".length());
	}
}
