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
import org.springframework.beans.factory.annotation.Autowired;

import com.geology.model.User;
import com.geology.service.UserService;

@Path("/user/v1")
public class UserLogin
{
	@Autowired
	private UserService userServ;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getResource(@Context HttpServletRequest request)
	{
		String input = null;
		try
		{
			input = IOUtils.toString(request.getInputStream());
			System.out.println(input);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(input == null)
		{
			return Response.status(400).encoding("null").build();
		}
		List<User> uList = userServ.checkUser();
		return Response.ok().entity(input).build();
	}
}
