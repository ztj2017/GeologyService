package com.geology.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public class SessionFilter implements ContainerRequestFilter
{
	@Context
	private HttpServletRequest request;

	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		if (!"user/v1/login".equals(requestContext.getUriInfo().getPath()))
		{
			HttpSession session = request.getSession();
			if (null == session.getAttribute("user"))
			{
				requestContext.abortWith(Response.status(403).build());
			}
		}

	}

}
