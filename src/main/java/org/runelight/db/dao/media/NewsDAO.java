package org.runelight.db.dao.media;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.runelight.view.dto.media.NewsDTO;

public final class NewsDAO {
	
	private static final Logger LOG = Logger.getLogger(NewsDAO.class);
	
	public static NewsDTO getNewsItem(Connection con, int id) {
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
			
			return NewsDTO.createNewsItemDTO(
				result.getInt("id"), result.getString("title"), result.getTimestamp("date"), result.getInt("category"), result.getString("body"), nextId, prevId
			);
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch a news item with the id " + id);
			return null;
		}
	}
	
	public static List<NewsDTO> getTitleNews(Connection con) {
		try {
			String sql = "CALL `media_getTitleNews`();";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.execute();
			ResultSet results = stmt.getResultSet();
			
			if(results == null) {
				return null;
			}
			
			List<NewsDTO> newsList = new LinkedList<>();
			while(results.next()) {
				newsList.add(NewsDTO.createTitleNewsDTO(
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
