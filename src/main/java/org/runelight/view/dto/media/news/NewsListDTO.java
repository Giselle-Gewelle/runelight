package org.runelight.view.dto.media.news;

import java.util.List;

public final class NewsListDTO {
	
	private List<NewsItemDTO> newsList;
	private int currentPage;
	private int pageCount;
	private int categoryId;
	
	public NewsListDTO(List<NewsItemDTO> newsList, int currentPage, int pageCount, int categoryId) {
		this.newsList = newsList;
		this.currentPage = currentPage;
		this.pageCount = pageCount;
		this.categoryId = categoryId;
	}
	
	public List<NewsItemDTO> getNewsList() {
		return newsList;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	
}
