package org.runelight.view.dto.media;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.runelight.util.DateUtil;

public final class NewsDTO {

	public enum NewsCategory {
		
		GAME_UPDATES(1, "Game Updates", "Game Update"),
		WEBSITE(2, "Website"),
		CUSTOMER_SUPPORT(3, "Customer Support"),
		TECHNICAL(4, "Technical"),
		BEHIND_THE_SCENES(6, "Behind the Scenes");
		
		
		private static Map<Integer, NewsCategory> categoryMap;
		
		static {
			categoryMap = new HashMap<>();
			
			for(NewsCategory cat : NewsCategory.values()) {
				categoryMap.put(cat.getId(), cat);
			}
		}
		
		public static NewsCategory forId(int id) {
			return categoryMap.get(id);
		}
		
		
		private int id;
		private String name1;
		private String name2;
		
		private NewsCategory(int id, String name) {
			this(id, name, null);
		}
		
		private NewsCategory(int id, String name1, String name2) {
			this.id = id;
			this.name1 = name1;
			this.name2 = name2;
		}
		
		public int getId() {
			return id;
		}
		
		public String getName1() {
			return name1;
		}
		
		public String getName2() {
			if(name2 == null)
				return name1;
			
			return name2;
		}
		
		public String getIconName() {
			return name1.toLowerCase().replace(" ", "_");
		}
		
	}
	
	private int id;
	private String title;
	private String date;
	private NewsCategory category;
	private String iconName;
	private String description;
	private String body;
	private int nextId;
	private int prevId;
	
	public static NewsDTO createNewsItemDTO(int id, String title, Date date, int category, String body, int nextId, int prevId) {
		NewsDTO dto = new NewsDTO();
		dto.id = id;
		dto.title = title;
		dto.date = DateUtil.LONG_NEWS_FORMAT.format(date);
		dto.category = NewsCategory.forId(category);
		dto.body = body;
		dto.nextId = nextId;
		dto.prevId = prevId;
		
		return dto;
	}
	
	public static NewsDTO createTitleNewsDTO(int id, String title, Date date, String iconName, String description) {
		NewsDTO dto = new NewsDTO();
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
