package org.runelight.upload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.runelight.Config;

public final class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 8395194421970786250L;
	
	private static final Logger LOG = Logger.getLogger(UploadServlet.class);
	
	@Override
	public void init() {
		Config.init();
		verifyDirectories();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String imageName = request.getParameter("name");
		if(imageName == null) {
			return;
		}
		
		String regex = "^[a-zA-Z0-9\\/_-]{5,50}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(imageName);
		if(!matcher.find()) {
			return;
		}
		
		imageName = imageName.replace("/", File.separator);
		
		File file = new File(System.getenv("TOMCAT_HOME") + File.separatorChar + "uploads" + File.separatorChar + imageName + ".png");
		if(!file.exists()) {
			try {
				response.sendError(404);
			} catch(IOException e) {
				LOG.error("IOException occurred while attempting to send the user to the 404 error page from an invalid image.", e);
			}
			
			return;
		}
		
		response.setContentType("image/png");
		
        try {
			response.getOutputStream().write(Files.readAllBytes(file.toPath()));
	        response.getOutputStream().close();
		} catch(IOException e) {
			LOG.error("IOException occurred while attempting to read image file.", e);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		
	}
	
	private void verifyDirectories() {
		if(System.getenv("TOMCAT_HOME") == null) {
			LOG.error("TOMCAT_HOME is not set!");
			return;
		}
		
		File file = new File(System.getenv("TOMCAT_HOME"));
		if(!file.exists() || !file.isDirectory()) {
			LOG.error("TOMCAT_HOME location does not exist!");
			return;
		}
		
		File uploadsFolder = new File(System.getenv("TOMCAT_HOME") + File.separatorChar + "uploads");
		if(!uploadsFolder.exists()) {
			uploadsFolder.mkdir();
			LOG.info("Uploads folder created.");
		}
		
		File newsIconFolder = new File(System.getenv("TOMCAT_HOME") + File.separatorChar + "uploads" + File.separatorChar + "newsIcons");
		File userUploadFolder = new File(System.getenv("TOMCAT_HOME") + File.separatorChar + "uploads" + File.separatorChar + "userUploads");
		if(!newsIconFolder.exists()) {
			newsIconFolder.mkdir();
			LOG.info("News icon folder created.");
		}
		if(!userUploadFolder.exists()) {
			userUploadFolder.mkdir();
			LOG.info("User upload folder created.");
		}
	}
	
}
