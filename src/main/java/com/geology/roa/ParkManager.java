package com.geology.roa;

import java.util.List;
import java.util.UUID;

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
import com.geology.model.Spots;
import com.geology.model.User;
import com.geology.service.SpotsService;
import com.geology.service.UserService;
import com.geology.utils.DateUtils;
import com.geology.utils.ErrorInfo;

@Path("/park/v1")
public class ParkManager
{
	private static final Logger LOGGER = Logger.getLogger(ParkManager.class);

	@Autowired
	private SpotsService spotServ;

	@Autowired
	private UserService userServ;

	@POST
	@Path("/spot")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSpot(@Context HttpServletRequest request)
	{
		LOGGER.info("add spot begin.");
		String input = null;
		Spots spot = null;
		try
		{
			input = IOUtils.toString(request.getInputStream());
			spot = JSON.parseObject(input, Spots.class);
		} catch (Exception e)
		{
			LOGGER.error("input stream to objec exception.");
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode("E0001");
			errorInfo.setCasue("input error.");
			return Response.status(400).entity(JSON.toJSONString(errorInfo)).build();
		}
		User u = userServ.getUserById(spot.getOwner());
		if (u.getUserName() == null || u.getRoleType() == 0)
		{
			LOGGER.error("owner is error.");
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode("E0005");
			errorInfo.setCasue("owner no rights.");
			return Response.status(400).entity(JSON.toJSONString(errorInfo)).build();
		}
		spot.setId(UUID.randomUUID().toString());
		spot.setUpdateTime(System.currentTimeMillis() + "");
		spotServ.addSpot(spot);
		return Response.ok().entity(new JSONObject()).build();
	}

	@GET
	@Path("/spots")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSpots(@Context HttpServletRequest request, @QueryParam("owner") String ownerId)
	{
		LOGGER.info("get all spots begin.");
		if (ownerId == null)
		{
			LOGGER.info("owner id is null.");
			return Response.status(400).entity(new JSONArray()).build();
		}
		Spots spots = new Spots();
		spots.setOwner(ownerId);
		List<Spots> spotsList = spotServ.queryByConditions(spots);
		for (Spots spot : spotsList)
		{
			String time = spot.getUpdateTime();
			spot.setUpdateTime(DateUtils.stampToDate(time));
		}
		return Response.ok().entity(JSON.toJSONString(spotsList)).build();
	}

	@GET
	@Path("/spots/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpot(@Context HttpServletRequest request, @PathParam("id") String spotId,
			@QueryParam("owner") String ownerId)
	{
		LOGGER.info("get spot begin.");
		if (ownerId == null)
		{
			LOGGER.info("owner id is null.");
			return Response.status(400).entity(new JSONArray()).build();
		}
		Spots spots = new Spots();
		spots.setOwner(ownerId);
		spots.setId(spotId);
		List<Spots> spotsList = spotServ.queryByConditions(spots);
		for (Spots spot : spotsList)
		{
			String time = spot.getUpdateTime();
			spot.setUpdateTime(DateUtils.stampToDate(time));
		}
		return Response.ok().entity(JSON.toJSONString(spotsList)).build();
	}

}
