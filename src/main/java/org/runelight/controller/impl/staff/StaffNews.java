package org.runelight.controller.impl.staff;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
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
				uploadIcon();
				break;
		}
	}
	
	private boolean uploadIcon() {
		try {
			Part filePart = getRequest().getPart("file");
			if(filePart == null) {
				return false;
			}
			
			if(filePart.getSize() > Short.MAX_VALUE) {
				return false;
			}
			
			LOG.info("image type = " + filePart.getContentType());
			if(!filePart.getContentType().equals("image/png")) {
				return false;
			}
		} catch(IOException | ServletException e) {
			LOG.error("Exception occurred while attempting to upload a new file.", e);
		}
		
		return false;
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
		if(files == null || files.length < 1) {
			return;
		}
		
		if(setData) {
			JSONObject json = new JSONObject();
			JSONArray nameArray = new JSONArray();
			
			for(File file : files) {
				nameArray.put(file.getName().replace(".png", ""));
			}
			
			json.put("iconList", nameArray);
			
			setJsonData(json);
		} else {
			String[] fileNames = new String[files.length];
			for(int i = 0; i < files.length; i++) {
				fileNames[i] = files[i].getName().replace(".png", "");
			}
			
			getRequest().setAttribute("iconFiles", fileNames);
		}
	}
	
}
