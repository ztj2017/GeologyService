package com.geology.roa;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.geology.model.User;
import com.geology.service.UserService;
import com.geology.utils.ErrorInfo;

@Path("/user/v1")
public class UserLogin
{
	private static final Logger LOGGER = Logger.getLogger(UserLogin.class);
	
	@Autowired
	private UserService userServ;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@Context HttpServletRequest request)
	{
		LOGGER.info("user login check begin.");
		String input = null;
		try
		{
			input = IOUtils.toString(request.getInputStream());
		} catch (IOException e)
		{
			LOGGER.error("input stream to string IOExeption.");
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode("E0001");
			errorInfo.setCasue("input stream to string IOExeption");
			return Response.status(400).entity(JSON.toJSONString(errorInfo)).build();
		}
		User user = JSON.parseObject(input, User.class);
		if(user.getUserName() == null || user.getPassPharse() == null)
		{
			LOGGER.error("user name or password is empty.");
			ErrorInfo errorInfo = new ErrorInfo("E0002", "user name or password is empty.");
			return Response.status(400).entity(JSON.toJSONString(errorInfo)).build();
		}
		List<User> uList = userServ.checkUser(user);
		if(uList != null && uList.size() > 0 )
		{
			LOGGER.info("user login success.");
			return Response.ok().build();
		}
		LOGGER.error("ulogin failed. user is " + user.getUserName());
		ErrorInfo errorInfo = new ErrorInfo("E0002", "login failed.");
		return Response.status(400).entity(JSON.toJSONString(errorInfo)).build();
	}
}
