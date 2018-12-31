package com.geology.roa;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import com.alibaba.fastjson.JSONObject;

@Path("/image/v1")
public class UploadImage
{
	private static final List<String> ALLOWED_IMG_FORMAT = Arrays.asList("jpg", "jpeg", "png");

	@POST
	@Path("/upload/{userId}/{spotId}/{relicId}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadTemp(@Context HttpServletRequest request,
			@PathParam("userId") String userId, @PathParam("spotId") String spotId, @PathParam("relicId") String relicId,FormDataMultiPart form) throws UnsupportedEncodingException
	{
		final JSONObject obj = new JSONObject();
		List<String> imagePathList = new ArrayList<String>();
		List<FormDataBodyPart> bps = form.getFields("pic");
		for (FormDataBodyPart bp : bps)
		{
			InputStream uploadedInputStream = bp.getValueAs(InputStream.class);
			FormDataContentDisposition fileDetail = bp.getFormDataContentDisposition();
			if (request.getContentLength() > 20 * 1024 * 1024)
			{
				obj.put("error", "file size need <= 20M.");
				return Response.ok(obj, MediaType.APPLICATION_JSON).status(400).entity(obj).build();
			}

			final String fileName = new String(fileDetail.getFileName().getBytes("ISO-8859-1"), "UTF-8");
			String fileExt = fileName.substring(fileName.indexOf(".") + 1);
			if (!ALLOWED_IMG_FORMAT.contains(fileExt))
			{
				obj.put("error", "error file format.");
				return Response.ok(obj, MediaType.APPLICATION_JSON).status(400).entity(obj).build();
			}
			String uploadRoot = getUploadPath(request.getSession().getServletContext().getRealPath(""));
			String mkPath = "/upload/" + userId + "/" + spotId + "/" + relicId + "/";
			String uploadPath = uploadRoot + mkPath;
			File file = new File(uploadPath);
			if (!file.exists())
			{
				file.mkdirs();
			}

			final String uploadedFileLocation = uploadPath + fileName;
			try
			{
				cutImageTo16_9(uploadedInputStream, uploadedFileLocation, fileExt);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			imagePathList.add(mkPath + fileName);
		}
		obj.put("status", "ok");
		obj.put("location", imagePathList);
		return Response.ok(obj, MediaType.APPLICATION_JSON).status(200).entity(obj).build();
	}

	private void cutImageTo16_9(InputStream uploadedInputStream,String uploadedFileLocation, String fileExt) throws IOException
	{
		BufferedImage bufImage = ImageIO.read(uploadedInputStream);
		BufferedImage processed = null;
		int originH = bufImage.getHeight();
		int originW = bufImage.getWidth();
		if ((double) originW / originH > 1.7777777777777777D)
		{
			// 裁宽 16*H = 9*W
			int w = Math.round(16 * originH / 9.0F);
			int x = (originW - w) / 2;
			processed = bufImage.getSubimage(x, 0, w, originH);
		} else if ((double) originW / originH < 1.7777777777777777D)
		{
			// 裁高
			int h = Math.round(9 * originW / 16.0F);
			int y = (originH - h) / 2;
			processed = bufImage.getSubimage(0, y, originW, h);
		} else
		{
			System.out.println("no need cut");
			ImageIO.write(bufImage, fileExt, new File(uploadedFileLocation));
		}

		if (processed != null)
		{
			ImageIO.write(processed, fileExt, new File(uploadedFileLocation));
		}
	}

	public String getUploadPath(String ss)
	{
		int index = ss.indexOf("webapps");
		return ss.substring(0, index + "webapps".length());
	}
}
