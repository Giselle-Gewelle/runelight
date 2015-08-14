package org.runelight.db.dao.account;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.runelight.http.RequestHandler;
import org.runelight.security.Hashing;
import org.runelight.security.LoginSession;
import org.runelight.security.Password;
import org.runelight.util.DateUtil;
import org.runelight.view.dto.account.SessionCheckDTO;
import org.runelight.view.dto.account.UserDTO;
import org.runelight.view.dto.account.UserSessionDTO;

public final class LoginSessionDAO {
	
	private static final Logger LOG = Logger.getLogger(LoginSessionDAO.class);
	
	/**
	 * Gets the user login session details for the specified session ID.
	 * @param con The database connection instance. 
	 * @param sessionId The session ID to look for.
	 * @param secure Whether or not the current website section is a secure (https) section.
	 * @param mod The mod of the current website section.
	 * @param dest The user's current destination. 
	 * @param endDate The end date for the session.
	 * @return A UserSessionDTO, or null on failure.
	 */
	public static UserSessionDTO getLoginSessionDetails(Connection con, int sessionId, boolean secure, String mod, String dest, String endDate) {
		try {
			String hash = Hashing.generateSessionHash();
			
			CallableStatement stmt = con.prepareCall("CALL `account_getLoginSessionDetails`(?, ?, ?, ?, ?, ?);");
			stmt.setInt("in_sessionId", sessionId);
			stmt.setBoolean("in_secure", secure);
			stmt.setString("in_newMod", mod);
			stmt.setString("in_newDest", dest);
			stmt.setString("in_newHash", hash);
			stmt.setString("in_endDate", endDate);
			stmt.execute();
			
			ResultSet results = stmt.getResultSet();
			if(results == null || !results.next()) {
				return null;
			}
			
			int unreadMessages = 0;
			int supportQueries = 0;
			
			if(results.getBoolean("staff")) {
				// Staff Unread Message Count
				stmt = con.prepareCall("CALL `account_ticketingGetUnreadCount`(?, ?);");
				stmt.setString("in_username", results.getString("username"));
				stmt.registerOutParameter("out_count", Types.INTEGER);
				stmt.execute();
				
				unreadMessages = stmt.getInt("out_count");
				
				// Unactioned Support Ticket Count
				stmt = con.prepareCall("CALL `staff_ticketingGetOpenTicketCount`(?);");
				stmt.registerOutParameter("out_count", Types.INTEGER);
				stmt.execute();
				
				supportQueries = stmt.getInt("out_count");
			}
			
			return new UserSessionDTO(
				results.getInt("accountId"), results.getString("username"), results.getBoolean("staff"), results.getBoolean("fmod"), results.getBoolean("pmod"), results.getString("currentIP"),
				sessionId, hash, secure, mod, dest, unreadMessages, supportQueries
			);
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch detailed login session info for [" + sessionId + "].", e);
			return null;
		}
	}
	
	/**
	 * Soft-kills a user login session by setting the endDate to the current date.
	 * @param con The database connection instance.
	 * @param sessionId The ID of the session to kill.
	 */
	public static void killSession(Connection con, int sessionId) {
		try {
			Calendar killCal = Calendar.getInstance();
			killCal.add(Calendar.MINUTE, -(LoginSession.IDLE_TIME + 5));
			
			String sql = "CALL `account_killLoginSession`(?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setInt("in_sessionId", sessionId);
			stmt.setString("in_date", DateUtil.SQL_DATETIME_FORMAT.format(killCal.getTime()));
			stmt.execute();
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to kill the login session [" + sessionId + "].", e);
		}
	}
	
	/**
	 * Looks for a valid login session that matches the given information.
	 * @param con The database connection instance.
	 * @param hash The hash that corresponds to the login session we're looking for.
	 * @param ip The ip that the login session is connected to.
	 * @param minDate The minimum date that the session has to be within.
	 * @return A SessionCheckDTO on success, or null on failure.
	 */
	public static SessionCheckDTO findSession(Connection con, String hash, String ip, Date minDate) {
		try {
			String sql = "CALL `account_findLoginSession`(?, ?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setString("in_hash", hash);
			stmt.setString("in_ip", ip);
			stmt.setString("in_minDate", DateUtil.SQL_DATETIME_FORMAT.format(minDate));
			stmt.execute();
			
			ResultSet results = stmt.getResultSet();
			if(results == null || !results.next()) {
				return null;
			}
			
			return new SessionCheckDTO(results.getInt("sessionId"), results.getBoolean("secure"), DateUtil.SQL_DATETIME_FORMAT.format(results.getTimestamp("endDate")));
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to find a user login session for [" + hash + "] : [" + ip + "].", e);
			return null;
		}
	}
	
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
			
			String sql = "CALL `account_submitLoginSession`(?, ?, ?, ?, ?, ?, ?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setInt("in_accountId", user.getAccountId());
			stmt.setString("in_ip", user.getCurrentIP());
			stmt.setString("in_sessionHash", hash);
			stmt.setString("in_date", DateUtil.SQL_DATETIME_FORMAT.format(date));
			stmt.setString("in_endDate", DateUtil.SQL_DATETIME_FORMAT.format(endDate));
			stmt.setString("in_mod", mod);
			stmt.setString("in_dest", dest);
			stmt.setBoolean("in_secure", RequestHandler.SECURE_MODS.contains(mod));
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
			
			UserDTO user = new UserDTO(new Password(results.getString("passwordHash"), results.getString("passwordSalt")));
			user.setAccountId(results.getInt("accountId"));
			return user;
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch user details for the user [" + username + "].", e);
			return null;
		}
	}
	
}
