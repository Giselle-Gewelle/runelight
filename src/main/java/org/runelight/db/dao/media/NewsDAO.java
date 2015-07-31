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
import org.runelight.view.dto.media.news.NewsItemDTO;
import org.runelight.view.dto.media.news.NewsListDTO;

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
					newsList.add(NewsItemDTO.createNewsListDTO(results.getInt("id"), results.getString("title"), results.getTimestamp("date"), results.getInt("category")));
				}
			}
			
			return new NewsListDTO(newsList.size() < 1 ? null : newsList, realPage, pageCount, cat);
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
			
			return NewsItemDTO.createNewsItemDTO(
				result.getInt("id"), result.getString("title"), result.getTimestamp("date"), result.getInt("category"), result.getString("body"), nextId, prevId
			);
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
				newsList.add(NewsItemDTO.createTitleNewsDTO(
					results.getInt("id"), results.getString("title"), results.getTimestamp("date"), results.getString("iconName"), results.getString("description")
				));
			}
			
			if(newsList.size() < 1) {
				return null;
			}
			
			return newsList;
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch the Title page news articles.");
			return null;
		}
	}
	
}
