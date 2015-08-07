package org.runelight.controller.impl.staff;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.runelight.controller.impl.media.News.NewsCategory;
import org.runelight.db.dao.staff.StaffNewsDAO;
import org.runelight.util.URLUtil;

public final class StaffNews extends StaffPage {

	private static final Logger LOG = Logger.getLogger(StaffNews.class);
	
	@Override
	public void init() {
		super.init();
		
		if(!isAuthorized()) {
			return;
		}
		
		switch(getDest()) {
			case "news/article.ws":
				//getIcons(false);
				getRequest().setAttribute("categoryList", NewsCategory.values());
				
				int articleId = URLUtil.getIntParam(getRequest(), "id");
				if(articleId > 1 && articleId < Short.MAX_VALUE) {
					// edit article page
					getRequest().setAttribute("editId", 0);
				}
				break;
			case "news/getIcons.ws":
				getIcons(true);
				break;
			case "news/uploadIcon.ws":
				JSONObject json = new JSONObject();
				String response = uploadIcon();
				
				int successIdx = response.indexOf("SUCCESS:");
				if(successIdx != -1) {
					json.put("newFileName", response.substring("SUCCESS:".length()));
					json.put("successful", true);
				} else {
					json.put("successful", false);
					json.put("errorMsg", response);
				}
				
				getRequest().getSession().setAttribute("isUploading", false);
				
				setJsonData(json);
				break;
		}
	}
	
	private String uploadIcon() {
		HttpSession session = getRequest().getSession();
		
		if(session.getAttribute("isUploading") != null) {
			if((boolean) session.getAttribute("isUploading")) {
				return "You are already uploading a file, please wait to try again.";
			}
		}
		
		try {
			session.setAttribute("isUploading", true);
			
			Part filePart = getRequest().getPart("iconFile");
			if(filePart == null) {
				return "Please select a valid file.";
			}
			
			String fileName = filePart.getSubmittedFileName();
			if(fileName == null) {
				return "Invalid file name.";
			}
			
			String contentType = filePart.getContentType();
			if(contentType == null || !contentType.equals("image/png")) {
				return "Only images in PNG format are allowed.";
			}
			
			if(fileName.lastIndexOf(".png") != fileName.length() - 4) {
				return "Only images in PNG format are allowed.";
			}
			
			InputStream data = filePart.getInputStream();
			BufferedImage image = ImageIO.read(data);
			if(image == null) {
				return "The file selected is not a valid image file.";
			}
			
			Image tmp = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		    BufferedImage after = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);

		    Graphics2D g2d = after.createGraphics();
		    g2d.drawImage(tmp, 0, 0, null);
		    g2d.dispose();
			
		    String name = "NEWSIMG" + new Date().getTime();
		    File outputFile = new File(System.getenv("TOMCAT_HOME") + File.separatorChar + "uploads" + File.separatorChar + "newsIcons" + File.separatorChar + name + ".png");
			ImageIO.write(after, "png", outputFile);
			return "SUCCESS:" + name;
		} catch(Throwable t) {
			return "An error has occured.";
		}
	}
	
	private void getIcons(boolean setData) {
		File uploadDir = new File(System.getenv("TOMCAT_HOME") + File.separatorChar + "uploads");
		if(!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		
		File iconDir = new File(System.getenv("TOMCAT_HOME") + File.separatorChar + "uploads" + File.separatorChar + "newsIcons");
		if(!iconDir.exists()) {
			iconDir.mkdir();
		}
		
		File[] files = iconDir.listFiles();
		
		if(setData) {
			JSONObject json = new JSONObject();
			
			if(files == null || files.length < 1) {
				json.put("iconCount", 0);
				setJsonData(json);
				return;
			}
			
			json.put("iconCount", files.length);
			
			JSONArray nameArray = new JSONArray();
			
			for(File file : files) {
				nameArray.put(file.getName().replace(".png", ""));
			}
			
			json.put("iconList", nameArray);
			setJsonData(json);
		} else {
			if(files == null || files.length < 1) {
				return;
			}
			
			String[] fileNames = new String[files.length];
			for(int i = 0; i < files.length; i++) {
				fileNames[i] = files[i].getName().replace(".png", "");
			}
			
			getRequest().setAttribute("iconFiles", fileNames);
		}
	}
	
}
