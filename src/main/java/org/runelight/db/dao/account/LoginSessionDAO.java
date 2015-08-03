package org.runelight.db.dao.account;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.apache.log4j.Logger;
import org.runelight.security.Hashing;
import org.runelight.security.Password;
import org.runelight.util.DateUtil;
import org.runelight.view.dto.account.UserDTO;

public final class LoginSessionDAO {
	
	private static final Logger LOG = Logger.getLogger(LoginSessionDAO.class);
	
	/**
	 * Grabs the number of login attempts the user at the specified IP has made in the given timeFrame.
	 * @param con The database connection instance.
	 * @param ip The ip to look for.
	 * @param timeFrame The time-frame to look in.
	 * @param maxAttempts The maximum number of attempts to look for.
	 * @return The number of attempts made in the given time-frame.
	 */
	public static int getLoginAttemptsForTimeFrame(Connection con, String ip, Date timeFrame, int maxAttempts) {
		try {
			String sql = "CALL `account_loginFloodCheck`(?, ?, ?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setString("in_ip", ip);
			stmt.setString("in_dateCheck", DateUtil.SQL_DATETIME_FORMAT.format(timeFrame));
			stmt.setInt("in_maxAttempts", maxAttempts + 3);
			stmt.registerOutParameter("out_attempts", Types.TINYINT);
			stmt.execute();
			
			return stmt.getInt("out_attempts");
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to check the latest login attempts for [" + ip + "].", e);
			return 0;
		}
	}
	
	/**
	 * Submits a user login session to the database.
	 * @param con The database connection instance.
	 * @param user The user object (UserDTO) containing the user's accountId and current IP address.
	 * @param date The current date (also known as startDate).
	 * @param endDate The end date for the session. This is the time that the session will expire after if the user is inactive or in a secure section of the website (account related section).
	 * @param mod The mod that this session started at.
	 * @param dest The dest that this session started at.
	 * @return A string containing the user's new session identifier hash, or null on failure.
	 */
	public static String submitLoginSession(Connection con, UserDTO user, Date date, Date endDate, String mod, String dest) {
		try {
			String hash = Hashing.generateSessionHash();
			
			String sql = "CALL `account_submitLoginSession`(?, ?, ?, ?, ?, ?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setInt("in_accountId", user.getAccountId());
			stmt.setString("in_ip", user.getCurrentIP());
			stmt.setString("in_sessionHash", hash);
			stmt.setString("in_date", DateUtil.SQL_DATETIME_FORMAT.format(date));
			stmt.setString("in_endDate", DateUtil.SQL_DATETIME_FORMAT.format(endDate));
			stmt.setString("in_mod", mod);
			stmt.setString("in_dest", dest);
			stmt.execute();
			
			return hash;
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to submit a login session for the user [" + user.getAccountId() + "].", e);
			return null;
		}
	}
	
	/**
	 * Fetches a user's accountId and password based off of a given username.
	 * @param con The database connection instance.
	 * @param username The username to look for.
	 * @param date The date of this request.
	 * @param ip The IP address of the user making this request.
	 * @return The desired user object (UserDTO), or null if a SQLException is thrown or the user is not found in the database.
	 */
	public static UserDTO getUserInfo(Connection con, String username, Date date, String ip) {
		try {
			String sql = "CALL `account_getUserForUsername`(?, ?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setString("in_username", username);
			stmt.setString("in_date", DateUtil.SQL_DATETIME_FORMAT.format(date));
			stmt.setString("in_ip", ip);
			stmt.execute();
			
			ResultSet results = stmt.getResultSet();
			if(results == null || !results.next()) {
				return null;
			}
			
			UserDTO user = new UserDTO();
			user.setAccountId(results.getInt("accountId"));
			user.setPassword(new Password(results.getString("passwordHash"), results.getString("passwordSalt")));
			return user;
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch user details for the user [" + username + "].", e);
			return null;
		}
	}
	
}
