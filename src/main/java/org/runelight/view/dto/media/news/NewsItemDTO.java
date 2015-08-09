package org.runelight.view.dto.media.news;

import java.util.Date;

import org.runelight.controller.impl.media.News.NewsCategory;
import org.runelight.util.DateUtil;

public final class NewsItemDTO {
	
	private int id;
	private String title;
	private String date;
	private NewsCategory category;
	private String iconName;
	private String description;
	private String body;
	private int nextId;
	private int prevId;
	
	public static NewsItemDTO createNewsListDTO(int id, String title, Date date, int category) {
		NewsItemDTO dto = new NewsItemDTO();
		dto.id = id;
		dto.title = title;
		dto.date = DateUtil.SHORT_NEWS_FORMAT.format(date);
		dto.category = NewsCategory.forId(category);
		
		return dto;
	}
	
	public static NewsItemDTO createNewsItemDTO(int id, String title, Date date, int category, String description, String body, String iconName, int nextId, int prevId) {
		NewsItemDTO dto = new NewsItemDTO();
		dto.id = id;
		dto.title = title;
		dto.date = DateUtil.LONG_NEWS_FORMAT.format(date);
		dto.category = NewsCategory.forId(category);
		dto.description = description;
		dto.body = body;
		dto.iconName = iconName;
		dto.nextId = nextId;
		dto.prevId = prevId;
		
		return dto;
	}
	
	public static NewsItemDTO createTitleNewsDTO(int id, String title, Date date, String iconName, String description) {
		NewsItemDTO dto = new NewsItemDTO();
		dto.id = id;
		dto.title = title;
		dto.date = DateUtil.SHORT_NEWS_FORMAT.format(date);
		dto.iconName = iconName;
		dto.description = description;
		
		return dto;
	}
	
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDate() {
		return date;
	}
	
	public NewsCategory getCategory() {
		return category;
	}
	
	public String getIconName() {
		return iconName;
	}

	public String getDescription() {
		return description;
	}

	public String getBody() {
		return body;
	}
	
	public int getNextId() {
		return nextId;
	}
	
	public int getPrevId() {
		return prevId;
	}
	
}
