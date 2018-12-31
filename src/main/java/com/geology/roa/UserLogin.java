package com.geology.roa;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import com.alibaba.fastjson.JSONObject;
import com.geology.model.User;
import com.geology.service.UserService;
import com.geology.utils.ErrorInfo;

@Path("/user/v1")
public class UserLogin
{
	private static final Logger LOGGER = Logger.getLogger(UserLogin.class);
	
	private static final String SOURCE = "ABCDEFGKJL0123456789";
	
	private static ConcurrentHashMap<String, Object>  cacheMap = new ConcurrentHashMap<String, Object>();
	
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
			ErrorInfo errorInfo = new ErrorInfo("E0002", "user name or password or code is empty.");
			return Response.status(400).entity(JSON.toJSONString(errorInfo)).build();
		}

		List<User> uList = userServ.checkUser(user);
		if(uList != null && uList.size() > 0 )
		{
			LOGGER.info("user login success.");
			User u = uList.get(0);
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(60 * 30);
			session.setAttribute("user", u);
			JSONObject json = new JSONObject();
			json.put("id", u.getId());
			return Response.ok().entity(json).build();
		}
		LOGGER.error("login failed. user is " + user.getUserName());
		ErrorInfo errorInfo = new ErrorInfo("E0002", "login failed.");
		return Response.status(400).entity(JSON.toJSONString(errorInfo)).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@Context HttpServletRequest request, @PathParam("id") String userId)
	{
		LOGGER.info("enter get user by id, id= " + userId);
		User u = userServ.getUserById(userId);
		JSONObject json = new JSONObject();
		json.put("id", u.getId());
		json.put("userName", u.getUserName());
		json.put("roleType", u.getRoleType());
		json.put("parentId", u.getId());
		if(u.getParentId() != null){
			json.put("parentId", u.getParentId());
		}
		return Response.ok().entity(json).build();
	}
	
	@GET
	@Path("/check-session/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSession(@Context HttpServletRequest request, @PathParam("id") String userId)
	{
		Object session = request.getSession().getAttribute(userId);
		if(session == null)
		{
			LOGGER.error("sessionCode is null.");
			ErrorInfo errorInfo = new ErrorInfo("E0002", "code has expired, refresh it.");
			return Response.status(400).entity(JSON.toJSONString(errorInfo)).build();
		}
		LOGGER.info("check sesion success.");
		return Response.ok().build();
	}
	
	@GET
	@Path("/code")
	public void getCode(@Context HttpServletRequest request, @Context HttpServletResponse response, @QueryParam("t") String flag)
	{
		if(flag == null)
		{
			return;
		}
		response.setContentType("image/jpeg");
		BufferedImage buffer = new BufferedImage(90, 40, BufferedImage.TYPE_INT_RGB);
		Graphics g = buffer.getGraphics();
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, 90, 40);
		g.setColor(Color.BLACK);
		g.drawRect(2, 2, 84, 34);
		g.setColor(new Color(88, 66, 120));
		g.setFont(new Font("宋体", Font.BOLD, 28));
		String checkCode = getCode(4);
		cacheMap.put(flag, checkCode); //code and expiretime 
		g.drawString(checkCode, 10, 30);
		g.dispose();
		try
		{
			ImageIO.write(buffer, "jpg",  response.getOutputStream());
		} catch (IOException e)
		{
			LOGGER.error("generate code error.");
			return;
		}
		LOGGER.info("generate code success.");
	}
	
	private String getCode(int len)
	{
		char ch[] = new char[len];
		Random r = new Random();
		for (int i = 0; i < len; i++)
		{
			ch[i] = SOURCE.charAt(r.nextInt(SOURCE.length()));
		}
		return new String(ch);
	}
	
}
