package org.runelight.db.dao.media;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.runelight.controller.impl.media.News;
import org.runelight.dto.NewsItemDTO;
import org.runelight.dto.NewsListDTO;
import org.runelight.util.DateUtil;

public final class NewsDAO {
	
	private static final Logger LOG = Logger.getLogger(NewsDAO.class);
	
	public static NewsListDTO getNewsList(Connection con, int cat, int page) {
		try {
			String sql = "CALL `media_getNewsList`(?, ?, ?, ?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setInt("in_cat", cat);
			stmt.setInt("in_page", page);
			stmt.setInt("in_limit", News.ARTICLES_PER_PAGE);
			stmt.registerOutParameter("out_pageCount", Types.INTEGER);
			stmt.registerOutParameter("out_realPage", Types.INTEGER);
			stmt.execute();
			ResultSet results = stmt.getResultSet();

			List<NewsItemDTO> newsList = new LinkedList<>();
			int pageCount = stmt.getInt("out_pageCount");
			int realPage = stmt.getInt("out_realPage");
			
			if(results != null) {
				while(results.next()) {
					NewsItemDTO item = new NewsItemDTO();
					item.setId(results.getInt("id"));
					item.setTitle(results.getString("title"));
					item.setDate(DateUtil.SHORT_NEWS_FORMAT.format(results.getTimestamp("date")));
					item.setCategory(results.getInt("category"));
					newsList.add(item);
				}
			}
			
			return new NewsListDTO(newsList, realPage, pageCount, cat);
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch the news list with the parameters (" + cat + ", " + page + ")");
			return null;
		}
	}
	
	public static NewsItemDTO getNewsItem(Connection con, int id) {
		try {
			String sql = "CALL `media_getNewsItem`(?, ?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setInt("in_articleId", id);
			stmt.registerOutParameter("out_nextId", Types.INTEGER);
			stmt.registerOutParameter("out_prevId", Types.INTEGER);
			stmt.execute();
			
			ResultSet result = stmt.getResultSet();
			if(result == null || !result.next()) {
				return null;
			}
			
			int nextId = stmt.getInt("out_nextId");
			int prevId = stmt.getInt("out_prevId");
			if(nextId < 1) nextId = -1;
			if(prevId < 1) prevId = -1;
			
			NewsItemDTO item = new NewsItemDTO();
			item.setId(result.getInt("id"));
			item.setTitle(result.getString("title"));
			item.setDate(DateUtil.LONG_NEWS_FORMAT.format(result.getTimestamp("date")));
			item.setCategory(result.getInt("category"));
			item.setDescription(result.getString("description"));
			item.setBody(result.getString("body"));
			item.setIconName(result.getString("iconName"));
			item.setNextId(nextId);
			item.setPrevId(prevId);
			
			return item;
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch a news item with the id " + id);
			return null;
		}
	}
	
	public static List<NewsItemDTO> getTitleNews(Connection con) {
		try {
			String sql = "CALL `media_getTitleNews`();";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.execute();
			ResultSet results = stmt.getResultSet();
			
			if(results == null) {
				return null;
			}
			
			List<NewsItemDTO> newsList = new LinkedList<>();
			while(results.next()) {
				NewsItemDTO item = new NewsItemDTO();
				item.setId(results.getInt("id"));
				item.setTitle(results.getString("title"));
				item.setDate(DateUtil.SHORT_NEWS_FORMAT.format(results.getTimestamp("date")));
				item.setIconName(results.getString("iconName"));
				item.setDescription(results.getString("description"));
				
				newsList.add(item);
			}
			
			return newsList;
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch the Title page news articles.");
			return null;
		}
	}
	
}
