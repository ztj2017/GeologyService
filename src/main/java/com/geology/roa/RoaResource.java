package com.geology.roa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/res/v1")
public class RoaResource
{
	@GET
	@Path("/{id}")
	@Produces(value = { MediaType.TEXT_PLAIN })
	public Response getResource(@PathParam("id") String id)
	{
		return Response.ok().entity(id).build();
	}

	@GET
	@Path("/download")
	@Produces(value = { MediaType.APPLICATION_OCTET_STREAM })
	public Response dowloadResource()
	{
		File file = new File("D:/apache-tomcat-8.0.38-windows-x64.zip");
		Response rsp = null;

		try
		{
			InputStream in = new FileInputStream(file);
			rsp = Response.ok().header("Content-Disposition", "attachment;filename=" + file.getName()).entity(in)
					.build();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return rsp;
	}
}
