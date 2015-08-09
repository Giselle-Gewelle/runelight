package org.runelight.controller.impl.staff;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.runelight.controller.impl.media.News.NewsCategory;
import org.runelight.db.dao.media.NewsDAO;
import org.runelight.db.dao.staff.StaffNewsDAO;
import org.runelight.http.HttpRequestType;
import org.runelight.util.URLUtil;
import org.runelight.view.dto.media.news.NewsItemDTO;

public final class StaffNews extends StaffPage {

	private static final Logger LOG = Logger.getLogger(StaffNews.class);
	
	private int editId = 0;
	private int redirectId = 0;
	
	@Override
	public void init() {
		super.init();
		
		if(!isAuthorized()) {
			return;
		}
		
		switch(getDest()) {
			case "news/article.ws":
				getRequest().setAttribute("categoryList", NewsCategory.values());
				
				int editId = URLUtil.getIntParam(getRequest(), "id");
				if(editId > 1 && editId < Short.MAX_VALUE) {
					// edit article page
					NewsItemDTO newsItem = NewsDAO.getNewsItem(getDbConnection(), editId);
					if(newsItem != null) {
						getRequest().setAttribute("editArticle", newsItem);
						this.editId = editId;
					}
				}
				
				if(getRequestType().equals(HttpRequestType.POST)) {
					boolean validSubmission = validateSubmission();
					if(!validSubmission) {
						getRequest().setAttribute("submissionFailed", true);
					} else {
						getRequest().setAttribute("redirectId", redirectId);
					}
				}
				break;
			case "news/getIcons.ws":
				getIcons();
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
	
	private boolean validateSubmission() {
		HttpServletRequest r = getRequest();
		
		String title = r.getParameter("title");
		String category = r.getParameter("category");
		String description = r.getParameter("description");
		String article = r.getParameter("article");
		String iconName = r.getParameter("icon");
		
		if(title == null || category == null || description == null || article == null || iconName == null) {
			return false;
		}
		
		title = title.trim();
		category = category.trim();
		description = description.trim();
		article = article.trim();
		iconName = iconName.trim();
		
		if(title.equals("") || category.equals("") || description.equals("") || article.equals("") || iconName.equals("")) {
			return false;
		}
		
		if(title.length() < 1 || title.length() > 50) {
			return false;
		}
		if(description.length() < 1 || description.length() > 1024) {
			return false;
		}
		if(article.length() < 1 || article.length() > 65535) {
			return false;
		}
		
		try {
			if(NewsCategory.forId(Integer.parseInt(category)) == null) {
				return false;
			}
		} catch(NumberFormatException e) {
			return false;
		}
		
		LOG.info("past here");

		boolean iconFound = false;
		
		try {
			File iconDir = new File(System.getenv("TOMCAT_HOME") + File.separatorChar + "uploads" + File.separatorChar + "newsIcons");
			File[] files = iconDir.listFiles();
			
			for(File file : files) {
				if(iconName.equals(file.getName().replace(".png", ""))) {
					iconFound = true;
				}
			}
		} catch(Exception e) {
			return false;
		}
		
		if(!iconFound) {
			return false;
		}
		
		int returnCode = StaffNewsDAO.submitArticle(getDbConnection(), this.editId, getLoginSession().getUser().getAccountId(), title, Integer.parseInt(category), description, article, iconName);
		if(returnCode < 1) {
			return false;
		}
		
		this.redirectId = returnCode;
		return true;
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
	
	private void getIcons() {
		File uploadDir = new File(System.getenv("TOMCAT_HOME") + File.separatorChar + "uploads");
		if(!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		
		File iconDir = new File(System.getenv("TOMCAT_HOME") + File.separatorChar + "uploads" + File.separatorChar + "newsIcons");
		if(!iconDir.exists()) {
			iconDir.mkdir();
		}
		
		File[] files = iconDir.listFiles();
		
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
	}
	
}
