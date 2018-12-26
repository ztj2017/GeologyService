package com.geology.roa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.alibaba.fastjson.JSONObject;

@Path("/image/v1")
public class UploadImage
{
	private static final List<String> ALLOWEDFILEFORMAT = Arrays.asList("jpg", "jpeg", "png");

	@POST
	@Path("/upload/{userId}/{spotId}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadTemp(@FormDataParam("file") final InputStream uploadedInputStream,
			@FormDataParam("file") final FormDataContentDisposition fileDetail, @Context HttpServletRequest request,
			@PathParam("userId") String userId, @PathParam("spotId") String spotId)
	{
		final JSONObject obj = new JSONObject();
		final String fileName = fileDetail.getFileName();
		String fileExt = fileName.substring(fileName.indexOf(".") + 1);
		if (!ALLOWEDFILEFORMAT.contains(fileExt))
		{
			obj.put("error", "error file format.");
			return Response.ok(obj, MediaType.APPLICATION_JSON).status(400).entity(obj).build();
		}
		String uploadRoot = getUploadPath(request.getSession().getServletContext().getRealPath(""));
		String mkPath = "/upload/" + userId + "/" + spotId + "/";
		String uploadPath = uploadRoot + mkPath;
		File file = new File(uploadPath);
		if (!file.exists())
		{
			file.mkdirs();
		}

		final String uploadedFileLocation = uploadPath + fileName;
		try
		{
			FileUtils.copyInputStreamToFile(uploadedInputStream, new File(uploadedFileLocation));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		obj.put("status", "ok");
		obj.put("location", mkPath + fileName);
		return Response.ok(obj, MediaType.APPLICATION_JSON).status(200).entity(obj).build();
	}

	public String getUploadPath(String ss)
	{
		int index = ss.indexOf("webapps");
		return ss.substring(0, index + "webapps".length());
	}
}
