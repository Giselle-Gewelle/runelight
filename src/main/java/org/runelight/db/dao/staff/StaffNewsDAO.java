package org.runelight.db.dao.staff;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.apache.log4j.Logger;
import org.runelight.util.DateUtil;

public final class StaffNewsDAO {
	
	private static final Logger LOG = Logger.getLogger(StaffNewsDAO.class);
	
	public static boolean deleteArticle(Connection con, int articleId) {
		try {
			String sql = "CALL `staff_deleteNewsArticle`(?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setInt("in_articleId", articleId);
			stmt.registerOutParameter("out_returnCode", Types.BIT);
			stmt.execute();
			
			return (stmt.getByte("out_returnCode") == 1);
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to delete the news article [" + articleId + "].", e);
			return false;
		}
	}
	
	public static int submitArticle(Connection con, int updateId, int authorId, String title, int category, String description, String article, String iconName) {
		try {
			String sql = "CALL `staff_submitNewsArticle`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setInt("in_updateId", updateId);
			stmt.setInt("in_authorId", authorId);
			stmt.setString("in_date", DateUtil.SQL_DATETIME_FORMAT.format(new Date()));
			stmt.setString("in_title", title);
			stmt.setInt("in_category", category);
			stmt.setString("in_desc", description);
			stmt.setString("in_article", article);
			stmt.setString("in_icon", iconName);
			stmt.registerOutParameter("out_returnCode", Types.BIT);
			stmt.registerOutParameter("out_id", Types.INTEGER);
			stmt.execute();
			
			int returnCode = stmt.getByte("out_returnCode");
			if(returnCode == 0) {
				return -1;
			}
			
			int articleId = stmt.getInt("out_id");
			if(articleId < 1) {
				return -1;
			}
			
			return articleId;
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to submit a news article for addition/modification.", e);
			return -1;
		}
	}
	
}
