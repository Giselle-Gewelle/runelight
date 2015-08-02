package org.runelight.db.dao.account;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.apache.log4j.Logger;
import org.runelight.util.DateUtil;

public final class CreateAccountDAO {
	
	private static final Logger LOG = Logger.getLogger(CreateAccountDAO.class);
	
	public static boolean createAccount(Connection con, String username, String passwordHash, String passwordSalt, int ageRange, int countryCode, 
			Date date, String ip) {
		try {
			String sql = "CALL `account_createUserAccount`(?, ?, ?, ?, ?, ?, ?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setString("in_username", username);
			stmt.setString("in_passwordHash", passwordHash);
			stmt.setString("in_passwordSalt", passwordSalt);
			stmt.setInt("in_ageRange", ageRange);
			stmt.setInt("in_countryCode", countryCode);
			stmt.setString("in_date", DateUtil.SQL_DATETIME_FORMAT.format(date));
			stmt.setString("in_ip", ip);
			stmt.registerOutParameter("out_returnCode", Types.TINYINT);
			stmt.execute();
			
			byte returnCode = stmt.getByte("out_returnCode");
			return returnCode != 0;
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to create the account [" + username + "].", e);
			return false;
		}
	}
	
	public static int usernameExists(Connection con, String username) {
		try {
			String sql = "CALL `account_checkUsername`(?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setString("in_username", username);
			stmt.registerOutParameter("out_returnCode", Types.BIT);
			stmt.execute();
			
			byte returnCode = stmt.getByte("out_returnCode");
			
			return (returnCode == 1 ? 1 : 0);
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to check if the username [" + username + "] exists.", e);
			return -1;
		}
	}
	
}
