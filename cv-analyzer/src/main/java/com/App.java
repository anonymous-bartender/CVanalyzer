package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.annotation.MultipartConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import main.Engine;
import util.FileUtil;


/*
 * Run using command `mvn jetty:run`
 * use browser to navigate at http://localhost:3000/cv-analyzer/
 * 
 * Following API calls are present for this resource. Refer to Engine.java for logic used. 
 * 
 * POST	localhost:8080/cv-analyzer/upload -> upload file
 * GET	localhost:8080/cv-analyzer/ -> return base html
 * GET	localhost:8080/cv-analyzer/static/{id}/{id} -> return static content
 * 
 */

@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
@Path("/")
public class App {
	
	@GET
	@Produces({ MediaType.TEXT_HTML})
	public FileInputStream viewHome()
	{

		try {
			File f = new File("public/frontapp.html");
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.out.println ("[ERROR] File not found");
			return null;
		}
		
	}

	@GET
	@Path("/static/{type}/{name}")
	@Produces({ MediaType.WILDCARD})
	public FileInputStream staticContent(@PathParam("type")String type, @PathParam("name")String name)
	{

		try {
			File f = new File("public/"+type+"/"+name);
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.out.println ("[ERROR] "+"public/"+type+"/"+name+" file not found");
		}
		
		return null;
	}

	@GET
	@Path("/static/{type}/{name1}/{name2}")
	@Produces({ MediaType.WILDCARD})
	public FileInputStream staticContent2ndLevel(@PathParam("type")String type, @PathParam("name1")String name1,
			@PathParam("name2")String name2)
	{

		try {
			File f = new File("public/"+type+"/"+name1+"/"+name2);
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.out.println ("[ERROR] "+"public/"+type+"/"+name1+"/"+name2+" file not found");
		}
		return null;
	}


	@POST
	@Path("/upload")
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	@Produces({ MediaType.APPLICATION_JSON})
	public Response checking(
			@FormDataParam("attachment") InputStream  uploadedStream,
			@FormDataParam("attachment") FormDataContentDisposition fileDetail)
	{
		try {

			File dir = new File("data");
			if(!dir.exists()) dir.mkdirs();
			File downloadedFile = new File(dir, fileDetail.getFileName());
			if(downloadedFile.exists())  downloadedFile.delete();
			downloadedFile.createNewFile();

			FileUtil.saveToFile(uploadedStream, downloadedFile);

			Engine e = new Engine(downloadedFile.getAbsolutePath());
			if(!e.isSupported()) {
				String output = "{ \"Message\":\""+fileDetail.getFileName()+" is in not supported format. Suported formats are Text and PDF.\"}";
				return Response.status(405).entity(output).build();
			}

			e.run();
			String output = e.getAll();
			return Response.status(200).entity(output).build();
		}
		catch (Exception e) {
			e.printStackTrace();
			String output = "{ \"Message\":\" Error occurred at Server side. \"";
			return Response.status(500).entity(output).build();
		}
	}


}



