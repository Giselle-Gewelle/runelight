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
				LOG.info("fail1");
				return -1;
			}
			
			int articleId = stmt.getInt("out_id");
			if(articleId < 1) {
				LOG.info("fail2");
				return -1;
			}
			
			return articleId;
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to submit a news article for addition/modification.", e);
			return -1;
		}
	}
	
}
