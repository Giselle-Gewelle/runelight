package org.runelight.controller.impl.media;

import java.util.HashMap;
import java.util.Map;

import org.runelight.controller.Controller;
import org.runelight.db.dao.media.NewsDAO;
import org.runelight.dto.NewsItemDTO;
import org.runelight.dto.NewsListDTO;
import org.runelight.util.URLUtil;

public final class News extends Controller {

	public static final int ARTICLES_PER_PAGE = 20;
	
	public enum NewsCategory {
		
		GAME_UPDATES(1, "Game Updates", "Game Update"),
		WEBSITE(2, "Website"),
		CUSTOMER_SUPPORT(3, "Customer Support"),
		TECHNICAL(4, "Technical"),
		BEHIND_THE_SCENES(6, "Behind the Scenes");
		
		
		private static Map<String, NewsCategory> categoryMap;
		
		static {
			categoryMap = new HashMap<>();
			
			for(NewsCategory cat : NewsCategory.values()) {
				categoryMap.put(String.valueOf(cat.getId()), cat);
			}
		}
		
		public static NewsCategory forId(int id) {
			return categoryMap.get(id);
		}
		
		public static Map<String, NewsCategory> getCategoryMap() {
			return categoryMap;
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
			return name().toLowerCase();
		}
		
	}

	@Override
	public void init() {
		getRequest().setAttribute("categoryMap", NewsCategory.getCategoryMap());
		
		switch(getDest()) {
			case "newsitem.ws":
				fetchNewsItem();
				break;
			case "list.ws":
				fetchNewsList();
				break;
		}
	}
	
	private void fetchNewsList() {
		int cat = URLUtil.getIntParam(getRequest(), "cat");
		int page = URLUtil.getIntParam(getRequest(), "page");
		
		if(cat < 0 || NewsCategory.forId(cat) == null) {
			cat = 0;
		}
		if(page < 1) {
			page = 1;
		}
		if(page > Short.MAX_VALUE) {
			page = Short.MAX_VALUE;
		}
		
		NewsListDTO newsList = NewsDAO.getNewsList(getDbConnection(), cat, page);
		if(newsList == null) {
			getRequest().setAttribute("currentPage", 1);
			getRequest().setAttribute("pageCount", 1);
			getRequest().setAttribute("categoryId", 0);
		} else {
			getRequest().setAttribute("currentPage", newsList.getCurrentPage());
			getRequest().setAttribute("pageCount", newsList.getPageCount());
			getRequest().setAttribute("categoryId", newsList.getCategoryId());
			
			if(newsList.getNewsList().size() > 0) {
				getRequest().setAttribute("newsList", newsList.getNewsList());
			}
			
			if(newsList.getCategoryId() != 0) {
				getRequest().setAttribute("newsListTitle", NewsCategory.forId(newsList.getCategoryId()).getName2());
			}
		}
	}
	
	private void fetchNewsItem() {
		int id = URLUtil.getIntParam(getRequest(), "id");
		if(id < 1 || id > 16777215) {
			return;
		}
		
		NewsItemDTO newsItem = NewsDAO.getNewsItem(getDbConnection(), id);
		if(newsItem != null) {
			getRequest().setAttribute("newsItem", newsItem);
		}
	}

}
